package sheetplus.checkings.domain.contest.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sheetplus.checkings.domain.contest.dto.request.ContestRequestDto;
import sheetplus.checkings.domain.contest.dto.response.ContestResponseDto;
import sheetplus.checkings.domain.contest.service.ContestCRUDService;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("private/admin")
public class ContestController {

    private final ContestCRUDService contestCRUDService;

    @PostMapping("/contests/v1")
    public ResponseEntity<ContestResponseDto> createContest(
            @RequestBody ContestRequestDto contestRequestDto) {

        return ResponseEntity.created(URI.create(""))
                .body(contestCRUDService.createContest(contestRequestDto));
    }

    @PatchMapping("/contests/{contest}/v1")
    public ResponseEntity<ContestResponseDto> updateContest(
            @PathVariable("contest") Long contestId,
            @RequestBody ContestRequestDto contestRequestDto
    ){
        return ResponseEntity.ok(contestCRUDService
                                .updateContest(contestId, contestRequestDto));
    }

    @DeleteMapping("/contests/{contest}/v1")
    public ResponseEntity<Void> deleteContest(
            @PathVariable("contest") Long contestId
    ){
        contestCRUDService.deleteContest(contestId);

        return ResponseEntity.noContent().build();
    }


}
