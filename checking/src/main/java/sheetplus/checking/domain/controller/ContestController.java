package sheetplus.checking.domain.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sheetplus.checking.domain.dto.ContestRequestDto;
import sheetplus.checking.domain.dto.ContestResponseDto;
import sheetplus.checking.domain.service.ContestCRUDService;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("private/")
public class ContestController {

    private final ContestCRUDService contestCRUDService;

    @PostMapping("admin/contest/create")
    public ContestResponseDto createContest(
            @RequestBody ContestRequestDto contestRequestDto) {

        return contestCRUDService.createContest(contestRequestDto);
    }

    @PatchMapping("admin/contest/{contest}/update")
    public ResponseEntity<ContestResponseDto> updateContest(
            @PathVariable("contest") Long contestId,
            @RequestBody ContestRequestDto contestRequestDto
    ){
        return ResponseEntity.ok()
                        .body(contestCRUDService
                                .updateContest(contestId, contestRequestDto));
    }

    @DeleteMapping("admin/contest/{contest}/delete")
    public ResponseEntity<String> deleteContest(
            @PathVariable("contest") Long contestId
    ){
        contestCRUDService.deleteContest(contestId);

        return ResponseEntity.ok()
                .body("삭제완료");
    }


}
