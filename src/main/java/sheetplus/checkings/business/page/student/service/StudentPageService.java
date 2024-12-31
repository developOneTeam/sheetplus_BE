package sheetplus.checkings.business.page.student.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sheetplus.checkings.business.page.student.dto.StudentPageDto.ActivitiesResponseDto;
import sheetplus.checkings.business.page.student.dto.StudentPageDto.StudentHomePageResponseDto;
import sheetplus.checkings.domain.event.dto.EventDto.EventResponseDto;
import sheetplus.checkings.domain.member.entity.Member;
import sheetplus.checkings.domain.participatecontest.entity.ParticipateContest;
import sheetplus.checkings.domain.enums.EventCategory;
import sheetplus.checkings.domain.contest.repository.ContestRepository;
import sheetplus.checkings.domain.member.repository.MemberRepository;
import sheetplus.checkings.domain.participatecontest.repository.ParticipateContestStateRepository;
import sheetplus.checkings.exception.exceptionMethod.ApiException;
import sheetplus.checkings.util.JwtUtil;

import java.util.ArrayList;
import java.util.List;

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
    public StudentHomePageResponseDto readStudentHomePage(String token, Long contestId) {
        Member member = memberRepository.findById(jwtUtil.getMemberId(token))
                .orElseThrow(() -> new ApiException(MEMBER_NOT_FOUND));
        Integer eventCounts = 0;
        if(member.getMemberParticipateContestStates() != null){
            eventCounts = member.getMemberParticipateContestStates()
                    .getEventsCount();
        }




        return StudentHomePageResponseDto.builder()
                .studentName(member.getName())
                .studentMajor(member.getMajor())
                .eventCounts(eventCounts.toString())
                .events(contestRepository.findNowAfterEvents(
                        contestId))
                .build();
    }


    @Transactional(readOnly = true)
    public List<EventResponseDto> readStudentSchedulePage(Long contestId){
        return contestRepository.findTodayEvents(contestId);
    }

    @Transactional(readOnly = true)
    public ActivitiesResponseDto readStudentActivitiesPage(
            String token, Long contestId){
        ParticipateContest participateContest = participateContestStateRepository
                .findByMemberParticipateContestState_IdAndContestParticipateContestState_Id(
                        jwtUtil.getMemberId(token),contestId)
                .orElseThrow(() -> new ApiException(PARTICIPATE_NOT_FOUND));

        List<EventCategory> events = new ArrayList<>(participateContest.getEventTypeSet());


        return ActivitiesResponseDto
                .builder()
                .eventCounts(participateContest.getEventsCount().toString())
                .events(contestRepository.findParticipateEvents(contestId, events))
                .build();
    }


}
