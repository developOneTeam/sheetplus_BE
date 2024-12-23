package sheetplus.checkings.business.qrcode.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sheetplus.checkings.business.qrcode.dto.QrcodeRequestDto;
import sheetplus.checkings.business.qrcode.dto.QrcodeResponseDto;
import sheetplus.checkings.domain.contest.entity.Contest;
import sheetplus.checkings.domain.event.entity.Event;
import sheetplus.checkings.domain.member.entity.Member;
import sheetplus.checkings.domain.participatecontest.entity.ParticipateContest;
import sheetplus.checkings.domain.enums.ContestCons;
import sheetplus.checkings.domain.enums.EventType;
import sheetplus.checkings.domain.enums.MeritType;
import sheetplus.checkings.domain.enums.ReceiveCons;
import sheetplus.checkings.domain.event.repository.EventRepository;
import sheetplus.checkings.domain.member.repository.MemberRepository;
import sheetplus.checkings.domain.participatecontest.repository.ParticipateContestStateRepository;
import sheetplus.checkings.exception.exceptionMethod.ApiException;
import sheetplus.checkings.util.CryptoUtil;
import sheetplus.checkings.util.JwtUtil;

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
     * 2. Member, Event 조회
     * 3. EventType 체크 - CHECKING 타입인지 확인 / 아닐 경우 QR_NOT_VALID 예외 발생
     * 4. Event와 연관관계 맺은 Contest 정보 조회
     * 5. Event와 Contest EVENT_PROGRESS 상태인지 확인 / 아닐 경우 EVENT_NOT_PROGRESS 예외 발생
     * 6. Member, Contest 정보로 participateContest 조회 - 없을 경우 null Return
     * 7. participateContest이 null인 경우, participateContest객체 생성 및 연관관계 설정 후 SAVE
     * 8. participateContest이 null이 아닌 경우, Event의 타입 포함여부 확인 - 포함된 경우 EVENT_ALREADY_PARTICIPATE 예외 발생
     * 9. 8번 이후, 참여대회 수, 참여 대회 타입 저장 - 변경감지로 DB UPDATE 반영
     *
     * @param token - 사용자 정보
     * @param qrcodeRequestDto - 암호화된 Event 엔티티 PK
     * @return QrcodeResponseDto - 학생명/학번/이름명 Return
     */
    @Transactional
    public QrcodeResponseDto createParticipation(String token, QrcodeRequestDto qrcodeRequestDto){
        // 1번 로직
        Long id = cryptoUtil.decrypt(qrcodeRequestDto.getSecureCode());
        log.info("복호화된 qr코드 pk {}", id);

        // 2번 로직
        Member member = memberRepository.findById(jwtUtil.getMemberId(token))
                .orElseThrow(() -> new ApiException(MEMBER_NOT_FOUND));
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new ApiException(EVENT_NOT_FOUND));

        // 3번 로직
        if(event.getEventType().equals(EventType.NO_CHECKING)){
            throw new ApiException(QR_NOT_VALID);
        }

        // 4번 로직
        Contest contest = event.getEventContest();

        // 5번 로직
        if(!contest.getCons().equals(ContestCons.EVENT_PROGRESS)
            || !event.getEventCondition().equals(ContestCons.EVENT_PROGRESS)){
            throw new ApiException(EVENT_NOT_PROGRESS);
        }

        // 6번 로직
        ParticipateContest participateContest = participateContestStateRepository
                .findByMemberParticipateContestState_IdAndContestParticipateContestState_Id(
                        member.getId(), contest.getId())
                .orElseGet(() -> createNewParticipateContest(contest, member, event)); // 7번 로직

        if(participateContest != null){
            // 8번 로직
            if(participateContest.getEventTypeSet().contains(event.getEventCategory())){
                throw new ApiException(EVENT_ALREADY_PARTICIPATE);
            }

            // 9번 로직
            updateParticipateContest(participateContest, event);
        }

        return QrcodeResponseDto.builder()
                .studentName(member.getName())
                .studentId(member.getStudentId())
                .eventName(event.getName())
                .build();
    }

    // 9번 로직
    private static void updateParticipateContest(ParticipateContest participateContest, Event event) {
        participateContest.addCounts();
        participateContest.getEventTypeSet().add(event.getEventCategory());
    }

    // 7번 로직
    private ParticipateContest createNewParticipateContest(Contest contest, Member member, Event event) {
        ParticipateContest participateContest = ParticipateContest.builder()
                .eventsCount(1)
                .meritType(MeritType.PRIZE_NON_TARGET)
                .receiveCons(ReceiveCons.PRIZE_NOT_RECEIVED)
                .build();
        participateContest.setContestParticipateContestStates(contest);
        participateContest.setMemberParticipateContestStates(member);
        participateContest.getEventTypeSet().add(event.getEventCategory());
        participateContestStateRepository.save(participateContest);

        return null;
    }


}
