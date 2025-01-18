package sheetplus.checkings.business.page.student.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sheetplus.checkings.business.page.student.dto.StudentPageDto.ActivitiesResponseDto;
import sheetplus.checkings.business.page.student.dto.StudentPageDto.StudentHomeEventsInfoResponseDto;
import sheetplus.checkings.business.page.student.dto.StudentPageDto.StudentHomeMemberAndStampInfoResponseDto;
import sheetplus.checkings.domain.member.entity.Member;
import sheetplus.checkings.domain.participatecontest.entity.ParticipateContest;
import sheetplus.checkings.domain.contest.repository.ContestRepository;
import sheetplus.checkings.domain.member.repository.MemberRepository;
import sheetplus.checkings.domain.participatecontest.repository.ParticipateContestStateRepository;
import sheetplus.checkings.exception.exceptionMethod.ApiException;
import sheetplus.checkings.util.JwtUtil;

import static sheetplus.checkings.exception.error.ApiError.MEMBER_NOT_FOUND;
import static sheetplus.checkings.exception.error.ApiError.PARTICIPATE_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Slf4j
public class StudentPageService {

    private final MemberRepository memberRepository;
    private final ContestRepository contestRepository;
    private final ParticipateContestStateRepository participateContestStateRepository;
    private final JwtUtil jwtUtil;


    @Transactional(readOnly = true)
    public StudentHomeMemberAndStampInfoResponseDto readStudentHomeMemberAndStampInfo(String token, Long contestId) {
        Member member = memberRepository.findByIdAndWithGraph(jwtUtil.getMemberId(token))
                .orElseThrow(() -> new ApiException(MEMBER_NOT_FOUND));
        Integer count = participateContestStateRepository
                .participateCounts(member.getId(), contestId);


        return StudentHomeMemberAndStampInfoResponseDto.builder()
                .studentName(member.getName())
                .studentMajor(member.getMajor())
                .participateEventCounts(count == null ? 0 : count)
                .build();
    }

    @Transactional(readOnly = true)
    public StudentHomeEventsInfoResponseDto readStudentHomeEventInfo(Long contestId, String building) {
        return StudentHomeEventsInfoResponseDto.builder()
                .events(contestRepository.findNowAfterEvents(
                        contestId, building))
                .build();
    }




    @Transactional(readOnly = true)
    public ActivitiesResponseDto readStudentActivitiesPage(
            String token, Long contestId){
        ParticipateContest participateContest = participateContestStateRepository
                .findByMemberParticipateContestState_IdAndContestParticipateContestState_Id(
                        jwtUtil.getMemberId(token),contestId)
                .orElseThrow(() -> new ApiException(PARTICIPATE_NOT_FOUND));

        return ActivitiesResponseDto
                .builder()
                .eventCounts(participateContest.getEventsCount())
                .events(contestRepository.findParticipateEvents(participateContest.getId()))
                .build();
    }


}
