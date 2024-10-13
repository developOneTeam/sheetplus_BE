package sheetplus.checking.domain.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sheetplus.checking.domain.dto.ContestRequestDto;
import sheetplus.checking.domain.dto.ContestResponseDto;
import sheetplus.checking.domain.entity.Contest;
import sheetplus.checking.domain.repository.ContestRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class ContestCRUDService {

    private final ContestRepository contestRepository;

    @Transactional
    public ContestResponseDto createContest(ContestRequestDto contestRequestDto) {
        Contest contest = Contest.builder()
                .name(contestRequestDto.getName())
                .startDate(contestRequestDto.getStartDateTime())
                .endDate(contestRequestDto.getEndDateTime())
                .cons(contestRequestDto.getCondition())
                .build();
        Long id = contestRepository.save(contest).getId();

        return ContestResponseDto.builder()
                .id(id)
                .name(contestRequestDto.getName())
                .startDate(contestRequestDto.getStartDateTime())
                .endDate(contestRequestDto.getEndDateTime())
                .condition(contestRequestDto.getCondition().getMessage())
                .build();
    }

    @Transactional
    public ContestResponseDto updateContest(Long id, ContestRequestDto contestRequestDto) {
        Contest contest = contestRepository.findById(id).orElse(null);

        contest.updateContest(contestRequestDto);

        return ContestResponseDto.builder()
                .id(id)
                .name(contest.getName())
                .startDate(contest.getStartDate())
                .endDate(contest.getEndDate())
                .condition(contest.getCons().getMessage())
                .build();
    }

    @Transactional
    public void deleteContest(Long id) {
        contestRepository.deleteById(id);

    }


}
