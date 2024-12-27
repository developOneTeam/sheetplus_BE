package sheetplus.checkings.domain.contest.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import sheetplus.checkings.domain.contest.dto.request.ContestRequestDto;
import sheetplus.checkings.domain.contest.dto.response.ContestResponseDto;
import sheetplus.checkings.domain.contest.service.ContestCRUDService;
import sheetplus.checkings.util.response.Api;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("private/admin")
public class ContestController {

    private final ContestCRUDService contestCRUDService;

    @PostMapping("/contests")
    public Api<ContestResponseDto> createContest(
            @RequestBody ContestRequestDto contestRequestDto) {

        return Api.CREATED(contestCRUDService.createContest(contestRequestDto));
    }

    @PatchMapping("/contests/{contest}")
    public Api<ContestResponseDto> updateContest(
            @PathVariable("contest") Long contestId,
            @RequestBody ContestRequestDto contestRequestDto
    ){
        return Api.UPDATED(contestCRUDService
                                .updateContest(contestId, contestRequestDto));
    }

    @DeleteMapping("/contests/{contest}")
    public Api<String> deleteContest(
            @PathVariable("contest") Long contestId
    ){
        contestCRUDService.deleteContest(contestId);

        return Api.DELETE("삭제완료");
    }


}
