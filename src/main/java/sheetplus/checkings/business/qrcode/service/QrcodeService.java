package sheetplus.checkings.business.qrcode.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sheetplus.checkings.business.page.student.controller.StudentPageController;
import sheetplus.checkings.business.qrcode.dto.QrCodeDto.QrcodeCreateResponseDto;
import sheetplus.checkings.business.qrcode.dto.QrCodeDto.QrcodeRequestDto;
import sheetplus.checkings.business.qrcode.dto.QrCodeDto.QrcodeResponseDto;
import sheetplus.checkings.domain.contest.entity.Contest;
import sheetplus.checkings.domain.enums.*;
import sheetplus.checkings.domain.event.entity.Event;
import sheetplus.checkings.domain.member.entity.Member;
import sheetplus.checkings.domain.participatecontest.entity.ParticipateContest;
import sheetplus.checkings.domain.event.repository.EventRepository;
import sheetplus.checkings.domain.member.repository.MemberRepository;
import sheetplus.checkings.domain.participatecontest.repository.ParticipateContestStateRepository;
import sheetplus.checkings.exception.exceptionMethod.ApiException;
import sheetplus.checkings.util.CryptoUtil;
import sheetplus.checkings.util.JwtUtil;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static sheetplus.checkings.exception.error.ApiError.*;

@RequiredArgsConstructor
@Service
@Slf4j
public class QrcodeService {

    private final MemberRepository memberRepository;
    private final EventRepository eventRepository;
    private final ParticipateContestStateRepository participateContestStateRepository;
    private final CryptoUtil cryptoUtil;
    private final JwtUtil jwtUtil;

    /**
     *
     * QR코드 인증 로직 Flow
     * 1. Event PK 복호화
     * 2. 만료시간 복호화 및 검증 - 지정 시간을 벗어나거나 복호화 실패할 경우 예외 발생
     * 3. Member, Event 조회
     * 4. EventType 체크 - CHECKING 타입인지 확인 / 아닐 경우 QR_NOT_VALID 예외 발생
     * 5. Event와 연관관계 맺은 Contest 정보 조회
     * 6 Event와 Contest EVENT_PROGRESS 상태인지 확인 / 아닐 경우 EVENT_NOT_PROGRESS 예외 발생
     * 7. Member, Contest 정보로 participateContest 조회 - 없을 경우 null Return
     * 8. participateContest이 null인 경우, participateContest객체 생성 및 연관관계 설정 후 SAVE
     * 9. participateContest이 null이 아닌 경우, Event의 타입 포함여부 확인 - 포함된 경우 EVENT_ALREADY_PARTICIPATE 예외 발생
     * 10. 8번 이후, 참여대회 수, 참여 대회 타입 저장 - 변경감지로 DB UPDATE 반영
     *
     * @param memberId - 멤버 PK
     * @param qrcodeRequestDto - 암호화된 Event 엔티티 PK
     * @return QrcodeResponseDto - 학생명/학번/이름명 Return
     */
    @Transactional
    public QrcodeResponseDto createParticipation(Long memberId, QrcodeRequestDto qrcodeRequestDto){
        // 1번 로직
        Long id = cryptoUtil.decrypt(qrcodeRequestDto.getSecureCode());

        // 2번 로직
        LocalDateTime expireTime = cryptoUtil
                .decryptExpireTime(qrcodeRequestDto.getSecureExpireTime());
        if(expireTime == null){
            throw new ApiException(QR_EXPIRED_TIME_NOT_VALID);
        }

        if(LocalDateTime.now().isBefore(expireTime.plusSeconds(3))
        && LocalDateTime.now().isAfter(expireTime.minusSeconds(16))){
            log.info("만료시간: {}", expireTime);
            throw new ApiException(EXPIRED_QR_CODES);
        }

        // 3번 로직
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new ApiException(MEMBER_NOT_FOUND));
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new ApiException(EVENT_NOT_FOUND));

        // 4번 로직
        if(event.getEventType().equals(EventType.NO_CHECKING)){
            throw new ApiException(QR_NOT_VALID);
        }

        // 5번 로직
        Contest contest = event.getEventContest();

        // 6번 로직
        if(!contest.getCons().equals(ContestCons.EVENT_PROGRESS)
            || !event.getEventCondition().equals(ContestCons.EVENT_PROGRESS)){
            throw new ApiException(EVENT_NOT_PROGRESS);
        }

        // 7번 로직
        ParticipateContest participateContest = participateContestStateRepository
                .findByMemberParticipateContestState_IdAndContestParticipateContestState_Id(
                        member.getId(), contest.getId())
                .orElseGet(() -> createNewParticipateContest(contest, member, event)); // 8번 로직

        if(participateContest != null){
            // 9번 로직
            if(participateContest.getEventTypeSet().contains(event.getEventCategory())){
                throw new ApiException(EVENT_ALREADY_PARTICIPATE);
            }

            // 10번 로직
            updateParticipateContest(participateContest, event, member);
        }
        List<Link> links = new ArrayList<>();
        links.add(linkTo(methodOn(StudentPageController.class)
                .readStudentHomeMemberWithStampInfo("인증 토큰", contest.getId()))
                .withRel("학생 Home 페이지"));
        links.add(linkTo(methodOn(StudentPageController.class)
                .readStudentHomeEventInfo(contest.getId(), "인문과학관"))
                .withRel("학생 Home 페이지"));
        links.add(linkTo(methodOn(StudentPageController.class)
                .readStudentActivities("인증 토큰", contest.getId()))
                .withRel("학생 활동 페이지"));

        return QrcodeResponseDto.builder()
                .studentName(member.getName())
                .studentId(member.getStudentId())
                .eventName(event.getName())
                .link(links)
                .build();
    }

    // 10번 로직
    private static void updateParticipateContest(ParticipateContest participateContest, Event event, Member member) {
        participateContest.addCounts();
        participateContest.getEventTypeSet().add(event.getEventCategory());
        log.info("이벤트 참여정보 갱신 - 참여자 = {}, 참여 이벤트 = {}, 전체 이벤트 참여횟수 = {}, 참여 이벤트 유형 = {}",
                member.getName(),
                event.getName(), participateContest.getEventsCount(),
                participateContest.getEventTypeSet());
    }

    // 8번 로직
    private ParticipateContest createNewParticipateContest(Contest contest, Member member, Event event) {
        ParticipateContest participateContest = ParticipateContest.builder()
                .eventsCount(1)
                .meritType(MeritType.PRIZE_NON_TARGET)
                .receiveCons(ReceiveCons.PRIZE_NOT_RECEIVED)
                .build();
        participateContest.setContestParticipateContestStates(contest);
        participateContest.setMemberParticipateContestStates(member);
        event.setEventParticipateContest(participateContest);
        participateContest.getEventTypeSet().add(event.getEventCategory());
        participateContestStateRepository.save(participateContest);

        log.info("이벤트 참여정보 생성 - 참여자 = {}, 참여 이벤트 = {}, 참여 이벤트 유형 = {}", member.getName(),
                event.getName(), event.getEventType());
        return null;
    }


    /**
     * 1. 요청한 사용자 ROLE 검증 - ADMIN, SUPER_ADMIN 아니면 예외발생
     * 2. Event PK 복호화 및 조회 - 없으면 예외 발생
     * 3. Event PK 암호화
     * 4. 4번과 만료시간 암호화 키 전달
     *
     * @param token - member token
     * @param id - Event PK
     * @retrun QrcodeCreateResponseDto - 암호화 Event PK/만료시간 암호화 키
     */
    @Transactional
    public QrcodeCreateResponseDto createQrcode(String token, Long id){

        // 1번 로직
        Member member = memberRepository.findById(jwtUtil.getMemberId(token))
                .orElseThrow(() -> new ApiException(MEMBER_NOT_FOUND));
        if(!member.getMemberType().equals(MemberType.ADMIN)
            && !member.getMemberType().equals(MemberType.SUPER_ADMIN)){
            throw new ApiException(ROLE_ACCESS_DENIED);
        }

        // 2번 로직
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new ApiException(EVENT_NOT_FOUND));
        log.info("event 조회: {}", event.getName());

        // 3번 로직
        String secureId = cryptoUtil.encrypt(id);
        String secretExpireTime = cryptoUtil.encryptExpireTime(LocalDateTime.now());


        // 4번 로직
        return QrcodeCreateResponseDto.builder()
                .secureId(secureId)
                .secretExpireTime(secretExpireTime)
                .build();
    }

}
