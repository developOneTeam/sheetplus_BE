package sheetplus.checkings.business.page.admin.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sheetplus.checkings.business.page.admin.dto.AdminPageDto.*;
import sheetplus.checkings.domain.contest.entity.Contest;
import sheetplus.checkings.domain.event.entity.Event;
import sheetplus.checkings.domain.entry.dto.EntryDto.EntryInfoResponseDto;
import sheetplus.checkings.domain.entry.dto.EntryDto.EntryResponseDto;
import sheetplus.checkings.domain.event.dto.EventDto.EventResponseDto;
import sheetplus.checkings.domain.member.dto.MemberDto.MemberInfoResponseDto;
import sheetplus.checkings.domain.contest.repository.ContestRepository;
import sheetplus.checkings.domain.entry.repository.EntryRepository;
import sheetplus.checkings.domain.member.repository.MemberRepository;
import sheetplus.checkings.domain.participatecontest.dto.ParticipateContestDto.ParticipateInfoResponseDto;
import sheetplus.checkings.domain.participatecontest.repository.ParticipateContestStateRepository;
import sheetplus.checkings.exception.exceptionMethod.ApiException;

import java.time.LocalDateTime;
import java.util.*;

import static sheetplus.checkings.exception.error.ApiError.CONTEST_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminPageService {

    private final ContestRepository contestRepository;
    private final MemberRepository memberRepository;
    private final ParticipateContestStateRepository participateContestStateRepository;
    private final EntryRepository entryRepository;

    @Transactional(readOnly = true)
    public AdminStampStatsDto stampStats(Long contestId){
        Contest contest = contestRepository
                .findById(contestId).orElseThrow(() -> new ApiException(CONTEST_NOT_FOUND));

        long memberCounts = memberRepository.count();
        ParticipateInfoResponseDto participateInfoResponseDto = participateContestStateRepository
                .participateContestCounts(contest.getId());

        return AdminStampStatsDto.builder()
                .memberCounts(memberCounts)
                .participateInfoResponseDto(participateInfoResponseDto)
                .build();
    }

    @Transactional(readOnly = true)
    public AdminContestStatsDto contestStatsDto(Long contestId){
        Contest contest = contestRepository
                .findById(contestId).orElseThrow(() -> new ApiException(CONTEST_NOT_FOUND));
        List<Event> events = contest.getEvents();

        long remain = 0;
        long finish = 0;
        TreeSet<String> building = new TreeSet<>();
        HashSet<String> major = new HashSet<>();

        if(!events.isEmpty()){
            events.sort((o1, o2) -> {
                if(o1.getStartTime().equals(o2.getStartTime())){
                    return o1.getEndTime().compareTo(o2.getEndTime());
                }
                return o1.getStartTime().compareTo(o2.getStartTime());
            });


            for (int i = 0; i < events.size(); i++) {
                building.add(events.get(i).getBuilding());
                major.add(events.get(i).getMajor());
                LocalDateTime nowTime = LocalDateTime.now();

                if(events.get(i).getStartTime().getDayOfMonth()
                        <= nowTime.getDayOfMonth()
                        && events.get(i).getEndTime().getDayOfMonth()
                        >= nowTime.getDayOfMonth()
                        && (events.get(i).getStartTime().isAfter(nowTime)
                        || events.get(i).getEndTime().isAfter(nowTime))){
                    remain++;
                }else if(events.get(i).getEndTime().getDayOfMonth() < nowTime.getDayOfMonth() ||
                        (events.get(i).getEndTime().getDayOfMonth() == nowTime.getDayOfMonth()
                                && events.get(i).getEndTime().isBefore(nowTime))){
                    finish++;
                }
            }
        }
        EntryInfoResponseDto entryInfoResponseDto = entryRepository.entryInfoCounts();

        return AdminContestStatsDto.builder()
                .contestName(contest.getName())
                .contestStart(contest.getStartDate())
                .contestEnd(contest.getEndDate())
                .locationName(building.isEmpty() ? null : building.getFirst())
                .locationCounts(building.size())
                .remainEvents(remain)
                .finishEvents(finish)
                .notTodayEvents((events.size() - (remain + finish)))
                .entryMajorCounts(entryInfoResponseDto.getMajorCounts())
                .entryCounts(entryInfoResponseDto.getTotalCounts())
                .entryPreliminaryCounts(entryInfoResponseDto.getPreliminaryCounts())
                .entryFinalCounts(entryInfoResponseDto.getFinalCounts())
                .build();
    }

    @Transactional(readOnly = true)
    public AdminEventStatsDto eventStatsDto(Long contestId){
        Contest contest = contestRepository
                .findById(contestId).orElseThrow(() -> new ApiException(CONTEST_NOT_FOUND));
        List<Event> events = contest.getEvents();

        return AdminEventStatsDto
                .builder()
                .eventCounts(events.size())
                .allEvents(events.stream()
                        .map(p -> EventResponseDto.builder()
                                .id(p.getId())
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
                .build();
    }

    @Transactional(readOnly = true)
    public AdminEntryStatsDto entryStatsDto(Long contestId){
        Contest contest = contestRepository
                .findById(contestId).orElseThrow(() -> new ApiException(CONTEST_NOT_FOUND));

        List<EntryResponseDto> entryList = contest.getEntries().stream()
                .map(p -> EntryResponseDto.builder()
                        .id(p.getId())
                        .entryType(p.getEntryType().getMessage())
                        .professorName(p.getProfessorName())
                        .major(p.getMajor())
                        .teamNumber(p.getTeamNumber())
                        .leaderName(p.getLeaderName())
                        .location(p.getLocation())
                        .building(p.getBuilding())
                        .name(p.getName())
                        .build())
                .toList();

        return AdminEntryStatsDto
                .builder()
                .entryPageable(entryList)
                .build();
    }


    @Transactional(readOnly = true)
    public List<MemberInfoResponseDto> readDrawMemberList(Long contestId, Pageable pageable){
        Contest contest = contestRepository.findById(contestId)
                .orElseThrow(() -> new ApiException(CONTEST_NOT_FOUND));
        return participateContestStateRepository
                .drawMemberInfoRead(contest.getId(), pageable);
    }


    @Transactional(readOnly = true)
    public List<ContestInfoWithCounts> readContestInfoWithCounts(){
        return contestRepository.findContestInfoWithCounts();
    }


    /**
     *
     * Deprecated
     * 사유: 증정 기능 비즈니스 정책상 사용 보류
     *
     */
    public List<MemberInfoResponseDto> readPrizeMemberList(Long contestId){
        Contest contest = contestRepository.findById(contestId)
                .orElseThrow(() -> new ApiException(CONTEST_NOT_FOUND));
        return participateContestStateRepository
                .participateMemberInfoRead(contest.getId());
    }


}
