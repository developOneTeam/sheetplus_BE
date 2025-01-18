package sheetplus.checkings.business.page.common.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sheetplus.checkings.domain.contest.dto.ContestDto.ContestInfoResponseDto;
import sheetplus.checkings.domain.contest.entity.Contest;
import sheetplus.checkings.domain.contest.repository.ContestRepository;
import sheetplus.checkings.domain.event.dto.EventDto.EventExceptLinksResponseDto;
import sheetplus.checkings.exception.exceptionMethod.ApiException;

import java.util.List;

import static sheetplus.checkings.exception.error.ApiError.CONTEST_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommonPageService {

    private final ContestRepository contestRepository;

    @Transactional(readOnly = true)
    public List<ContestInfoResponseDto> readContestInfo(Pageable pageable){
        return contestRepository.findAllContestInfo(pageable);
    }

    @Transactional(readOnly = true)
    public List<EventExceptLinksResponseDto> readStudentSchedulePage(Long contestId, Pageable pageable){
        Contest contest = contestRepository.findById(contestId)
                .orElseThrow(() -> new ApiException(CONTEST_NOT_FOUND));

        return contestRepository.findTodayEvents(contest.getId(), pageable);
    }

}
