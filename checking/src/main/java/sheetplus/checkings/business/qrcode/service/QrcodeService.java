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

    @Transactional
    public QrcodeResponseDto createParticipation(String token, QrcodeRequestDto qrcodeRequestDto){
        // 1. qr코드 복호화 로직
        Long id = cryptoUtil.decrypt(qrcodeRequestDto.getSecureCode());
        log.info("복호화된 qr코드 pk {}", id);

        // 2. member 탐색
        Member member = memberRepository.findById(jwtUtil.getMemberId(token))
                .orElseThrow(() -> new ApiException(MEMBER_NOT_FOUND));
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new ApiException(EVENT_NOT_FOUND));

        if(event.getEventType().equals(EventType.NO_CHECKING)){
            // 예외 발생
            throw new ApiException(QR_NOT_VALID);
        }

        Contest contest = event.getEventContest();
        if(!contest.getCons().equals(ContestCons.EVENT_PROGRESS)
        || !event.getEventCondition().equals(ContestCons.EVENT_PROGRESS)){
            // 예외 발생
            throw new ApiException(EVENT_NOT_PROGRESS);
        }

        ParticipateContest participateContest = participateContestStateRepository
                .findByMemberParticipateContestState_IdAndContestParticipateContestState_Id(
                        member.getId(), contest.getId()).orElse(null);

        if(participateContest == null){
            participateContest = ParticipateContest.builder()
                    .eventsCount(1)
                    .meritType(MeritType.PRIZE_NON_TARGET)
                    .receiveCons(ReceiveCons.PRIZE_NOT_RECEIVED)
                    .build();
            participateContest.setContestParticipateContestStates(contest);
            participateContest.setMemberParticipateContestStates(member);
            participateContest.getEventTypeSet().add(event.getEventCategory());

            participateContestStateRepository.save(participateContest);
        }else{
            if(participateContest.getEventTypeSet().contains(event.getEventCategory())){
               throw new ApiException(EVENT_ALREADY_PARTICIPATE);
            }
            participateContest.addCounts();
            participateContest.getEventTypeSet().add(event.getEventCategory());

        }

        return QrcodeResponseDto.builder()
                .studentName(member.getName())
                .studentId(member.getStudentId())
                .eventName(event.getName())
                .build();
    }


}
