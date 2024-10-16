package sheetplus.checking.domain.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sheetplus.checking.domain.dto.EventResponseDto;
import sheetplus.checking.domain.dto.StudentPageActivitiesResponseDto;
import sheetplus.checking.domain.dto.StudentHomePageResponseDto;
import sheetplus.checking.domain.entity.Member;
import sheetplus.checking.domain.entity.ParticipateContest;
import sheetplus.checking.domain.entity.enums.EventCategory;
import sheetplus.checking.domain.repository.ContestRepository;
import sheetplus.checking.domain.repository.MemberRepository;
import sheetplus.checking.domain.repository.ParticipateContestStateRepository;
import sheetplus.checking.exception.ApiException;
import sheetplus.checking.util.JwtUtil;

import java.util.ArrayList;
import java.util.List;

import static sheetplus.checking.error.ApiError.MEMBER_NOT_FOUND;
import static sheetplus.checking.error.ApiError.PARTICIPATE_NOT_FOUND;

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

        return StudentHomePageResponseDto.builder()
                .studentName(member.getName())
                .studentMajor(member.getMajor())
                .eventCounts(member.getMemberParticipateContestStates()
                        .getEventsCount().toString())
                .events(contestRepository.findNowAfterEvents(
                        contestId))
                .build();
    }


    @Transactional(readOnly = true)
    public List<EventResponseDto> readStudentSchedulePage(Long contestId){
        return contestRepository.findTodayEvents(contestId);
    }

    @Transactional(readOnly = true)
    public StudentPageActivitiesResponseDto readStudentActivitiesPage(
            String token, Long contestId){
        ParticipateContest participateContest = participateContestStateRepository
                .findByMemberParticipateContestState_IdAndContestParticipateContestState_Id(
                        jwtUtil.getMemberId(token),contestId)
                .orElseThrow(() -> new ApiException(PARTICIPATE_NOT_FOUND));

        List<EventCategory> events = new ArrayList<>(participateContest.getEventTypeSet());


        return StudentPageActivitiesResponseDto
                .builder()
                .eventCounts(participateContest.getEventsCount().toString())
                .events(contestRepository.findParticipateEvents(contestId, events))
                .build();
    }


}
