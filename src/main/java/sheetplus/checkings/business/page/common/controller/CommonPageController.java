package sheetplus.checkings.business.page.common.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sheetplus.checkings.domain.contest.dto.ContestDto.ContestInfoResponseDto;
import sheetplus.checkings.business.page.common.service.CommonPageService;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("public/")
public class CommonPageController {

    private final CommonPageService commonPageService;

    @GetMapping("contests/v1")
    public ResponseEntity<List<ContestInfoResponseDto>> readContestInfo(
            @RequestParam(value = "offset", required = false)
            Integer offset,
            @RequestParam(value = "limit", required = false)
            Integer limit
    ){
        return ResponseEntity.ok(commonPageService.readContestInfo(PageRequest.of(offset-1, limit)));
    }

}
