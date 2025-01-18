package sheetplus.checkings.business.page.admin.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sheetplus.checkings.business.page.admin.dto.AdminPageDto.*;
import sheetplus.checkings.domain.contest.entity.Contest;
import sheetplus.checkings.domain.entry.dto.EntryDto.EntryExceptLinksResponseDto;
import sheetplus.checkings.domain.member.dto.MemberDto.MemberInfoResponseDto;
import sheetplus.checkings.domain.contest.repository.ContestRepository;
import sheetplus.checkings.domain.member.repository.MemberRepository;
import sheetplus.checkings.domain.participatecontest.dto.ParticipateContestDto.ParticipateInfoResponseDto;
import sheetplus.checkings.domain.participatecontest.repository.ParticipateContestStateRepository;
import sheetplus.checkings.exception.exceptionMethod.ApiException;

import java.util.*;

import static sheetplus.checkings.exception.error.ApiError.CONTEST_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminPageService {

    private final ContestRepository contestRepository;
    private final MemberRepository memberRepository;
    private final ParticipateContestStateRepository participateContestStateRepository;

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
        return contestRepository.findContestStats(contest.getId());
    }

    @Transactional(readOnly = true)
    public AdminEventStatsDto eventStatsDto(Long contestId, Pageable pageable){
        Contest contest = contestRepository
                .findById(contestId).orElseThrow(() -> new ApiException(CONTEST_NOT_FOUND));

        return contestRepository.findContestWithEvents(contest.getId(), pageable);
    }

    @Transactional(readOnly = true)
    public AdminEntryStatsDto entryStatsDto(Long contestId, Pageable pageable){
        Contest contest = contestRepository
                .findById(contestId).orElseThrow(() -> new ApiException(CONTEST_NOT_FOUND));

        List<EntryExceptLinksResponseDto> entryPageable =
                contestRepository.findContestWithEntries(contest.getId(), pageable);

        return AdminEntryStatsDto
                .builder()
                .entryPageable(entryPageable)
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
