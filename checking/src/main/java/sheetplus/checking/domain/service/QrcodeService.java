package sheetplus.checking.domain.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sheetplus.checking.domain.dto.QrcodeRequestDto;
import sheetplus.checking.domain.dto.QrcodeResponseDto;
import sheetplus.checking.domain.entity.Contest;
import sheetplus.checking.domain.entity.Event;
import sheetplus.checking.domain.entity.Member;
import sheetplus.checking.domain.entity.ParticipateContestState;
import sheetplus.checking.domain.entity.enums.ContestCondition;
import sheetplus.checking.domain.entity.enums.EventType;
import sheetplus.checking.domain.entity.enums.MeritType;
import sheetplus.checking.domain.entity.enums.ReceiveCondition;
import sheetplus.checking.domain.repository.EventRepository;
import sheetplus.checking.domain.repository.MemberRepository;
import sheetplus.checking.domain.repository.ParticipateContestStateRepository;
import sheetplus.checking.util.CryptoUtil;
import sheetplus.checking.util.JwtUtil;

import java.util.List;

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
                .orElse(null);
        Event event = eventRepository.findById(id).orElse(null);

        if(event.getEventType().equals(EventType.NO_CHECKING)){
            // 예외 발생
            throw new RuntimeException("QR코드 인증 대상이 아닙니다.");
        }

        Contest contest = event.getEventContest();
        if(!contest.getCondition().equals(ContestCondition.PROGRESS)
        || !event.getEventCondition().equals(ContestCondition.PROGRESS)){
            // 예외 발생
            throw new RuntimeException("현재 진행중인 행사가 아닙니다.");
        }


        List<ParticipateContestState> participateContestStateList =
                participateContestStateRepository
                        .findByMemberParticipateContestState_IdAndContestParticipateContestState_Id(
                                member.getId(), contest.getId());

        ParticipateContestState participateContestState;

        if(participateContestStateList.isEmpty()){
            participateContestState = ParticipateContestState.builder()
                    .eventsCount(1)
                    .meritType(MeritType.NON_TARGET)
                    .receiveCondition(ReceiveCondition.NOT_RECEIVED)
                    .build();
            participateContestState.setContestParticipateContestStates(contest);
            participateContestState.setMemberParticipateContestStates(member);
            participateContestState.getEventTypeSet().add(event.getEventCategory());

            participateContestStateRepository.save(participateContestState);
        }else{
            participateContestState = participateContestStateList.getFirst();
            if(participateContestState.getEventTypeSet().contains(event.getEventCategory())){
               throw new RuntimeException("이미 참여한 행사입니다.");
            }
            participateContestState.addCounts();
            participateContestState.getEventTypeSet().add(event.getEventCategory());

        }

        return QrcodeResponseDto.builder()
                .studentName(member.getName())
                .studentId(member.getStudentId())
                .eventName(event.getName())
                .build();
    }


}
