package sheetplus.checkings.business.page.common.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sheetplus.checkings.domain.contest.dto.response.ContestInfoResponseDto;
import sheetplus.checkings.domain.contest.entity.Contest;
import sheetplus.checkings.domain.contest.repository.ContestRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommonPageService {

    private final ContestRepository contestRepository;

    @Transactional(readOnly = true)
    public List<ContestInfoResponseDto> readContestInfo(){
        List<Contest> contests = contestRepository.findAll();

        return contests.stream()
                .map(a -> ContestInfoResponseDto.builder()
                        .contestId(a.getId())
                        .contestName(a.getName())
                        .build())
                .toList();
    }

}
