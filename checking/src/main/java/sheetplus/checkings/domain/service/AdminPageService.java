package sheetplus.checkings.domain.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sheetplus.checkings.domain.dto.*;
import sheetplus.checkings.domain.entity.Contest;
import sheetplus.checkings.domain.entity.Event;
import sheetplus.checkings.domain.repository.ContestRepository;
import sheetplus.checkings.domain.repository.EntryRepository;
import sheetplus.checkings.domain.repository.MemberRepository;
import sheetplus.checkings.domain.repository.ParticipateContestStateRepository;
import sheetplus.checkings.exception.ApiException;

import java.time.LocalDateTime;
import java.util.*;

import static sheetplus.checkings.error.ApiError.CONTEST_NOT_FOUND;
import static sheetplus.checkings.error.ApiError.EVENT_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminPageService {

    private final ContestRepository contestRepository;
    private final MemberRepository memberRepository;
    private final ParticipateContestStateRepository participateContestStateRepository;
    private final EntryRepository entryRepository;

    @Transactional(readOnly = true)
    public AdminHomeResponseDto adminHomeRead(Long contestId){
        Contest contest = contestRepository
                .findById(contestId).orElseThrow(() -> new ApiException(CONTEST_NOT_FOUND));
        long memberCounts = memberRepository.count();
        ParticipateInfoDto participateInfoDto = participateContestStateRepository
                .participateContestCounts(contest.getId());
        List<Event> events = contest.getEvents();

        if(events.isEmpty()){
            throw new ApiException(EVENT_NOT_FOUND);
        }

        events.sort((o1, o2) -> {
            if(o1.getStartTime().equals(o2.getStartTime())){
                return o1.getEndTime().compareTo(o2.getEndTime());
            }
            return o1.getStartTime().compareTo(o2.getStartTime());
        });


        long remain = 0;
        long finish = 0;
        TreeSet<String> building = new TreeSet<>();
        HashSet<String> major = new HashSet<>();


        for (int i = 0; i < events.size(); i++) {
            building.add(events.get(i).getBuilding());
            major.add(events.get(i).getMajor());
            LocalDateTime nowTime = LocalDateTime.now();

            if(events.get(i).getStartTime().getDayOfMonth()
                    == nowTime.getDayOfMonth() &&
                    (events.get(i).getStartTime().isAfter(nowTime)
                            || events.get(i).getEndTime().isAfter(nowTime))){
                remain++;
            }else{
                finish++;
            }
        }
        EntryInfoDto entryInfoDto = entryRepository.entryInfoCounts();
        List<EntryResponseDto> entryList = entryRepository.getEntry();


        return AdminHomeResponseDto.builder()
                .memberCounts(Long.toString(memberCounts))
                .completeEventMemberCounts(participateInfoDto
                        .getCompleteEventMemberCounts().toString())
                .moreThanOneCounts(participateInfoDto
                        .getMoreThanOneCounts().toString())
                .moreThanFiveCounts(participateInfoDto
                        .getMoreThanFiveCounts().toString())
                .contestName(contest.getName())
                .contestStart(contest.getStartDate())
                .contestEnd(contest.getEndDate())
                .locationName(building.getFirst())
                .locationCounts(String.valueOf(building.size()))
                .remainEvents(String.valueOf(remain))
                .finishEvents(String.valueOf(finish))
                .notTodayEvents(String.valueOf((events.size() - (remain + finish))))
                .entryMajorCounts(entryInfoDto.getMajorCounts().toString())
                .entryCounts(entryInfoDto.getTotalCounts().toString())
                .entryPreliminaryCounts(entryInfoDto.getPreliminaryCounts().toString())
                .entryFinalCounts(entryInfoDto.getFinalCounts().toString())
                .eventCounts(String.valueOf(events.size()))
                .allEvents(events.stream()
                        .map(p -> EventResponseDto.builder()
                                .secureId(p.getId().toString())
                                .name(p.getName())
                                .startTime(p.getStartTime())
                                .endTime(p.getEndTime())
                                .location(p.getLocation())
                                .building(p.getBuilding())
                                .speakerName(p.getSpeakerName())
                                .major(p.getMajor())
                                .conditionMessage(p.getEventCondition().getMessage())
                                .eventTypeMessage(p.getEventType().getMessage())
                                .categoryMessage(p.getEventCategory().getMessage())
                                .build())
                        .toList())
                .entryPageable(entryList)
                .build();
    }


    public List<MemberInfoDto> readDrawMemberList(Long contestId){
        Contest contest = contestRepository.findById(contestId)
                .orElseThrow(() -> new ApiException(CONTEST_NOT_FOUND));
        return participateContestStateRepository
                .participateMemberInfoRead(contest.getId());
    }

    public List<MemberInfoDto> readPrizeMemberList(Long contestId){
        Contest contest = contestRepository.findById(contestId)
                .orElseThrow(() -> new ApiException(CONTEST_NOT_FOUND));
        return participateContestStateRepository
                .drawMemberInfoRead(contest.getId());
    }


}
