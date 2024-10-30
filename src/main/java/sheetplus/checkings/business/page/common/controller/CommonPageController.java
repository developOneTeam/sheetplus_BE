package sheetplus.checkings.business.page.common.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sheetplus.checkings.domain.contest.dto.response.ContestInfoResponseDto;
import sheetplus.checkings.business.page.common.service.CommonPageService;
import sheetplus.checkings.util.response.Api;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("public/")
public class CommonPageController {

    private final CommonPageService commonPageService;

    @GetMapping("contest/read")
    public Api<List<ContestInfoResponseDto>> readContestInfo(){


        return Api.READ(commonPageService.readContestInfo());
    }

}
