package sheetplus.checkings.domain.contest.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sheetplus.checkings.domain.contest.dto.request.ContestRequestDto;
import sheetplus.checkings.domain.contest.dto.response.ContestResponseDto;
import sheetplus.checkings.domain.contest.entity.Contest;
import sheetplus.checkings.domain.contest.repository.ContestRepository;
import sheetplus.checkings.exception.exceptionMethod.ApiException;

import static sheetplus.checkings.exception.error.ApiError.COMMON_START_AFTER_END;
import static sheetplus.checkings.exception.error.ApiError.CONTEST_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Slf4j
public class ContestCRUDService {

    private final ContestRepository contestRepository;

    @Transactional
    public ContestResponseDto createContest(ContestRequestDto contestRequestDto) {
        if(contestRequestDto.getStartDateTime().isAfter(contestRequestDto.getEndDateTime())){
            throw new ApiException(COMMON_START_AFTER_END);
        }


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
        Contest contest = contestRepository.findById(id)
                .orElseThrow(() -> new ApiException(CONTEST_NOT_FOUND));;

        if(contestRequestDto.getStartDateTime().isAfter(contestRequestDto.getEndDateTime())){
            throw new ApiException(COMMON_START_AFTER_END);
        }

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
        if(contestRepository.existsById(id)){
            contestRepository.deleteById(id);
        }else{
            throw new ApiException(CONTEST_NOT_FOUND);
        }
    }


}
