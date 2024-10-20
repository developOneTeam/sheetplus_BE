package sheetplus.checkings.domain.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import sheetplus.checkings.domain.dto.AdminHomeResponseDto;
import sheetplus.checkings.domain.dto.MemberInfoDto;
import sheetplus.checkings.domain.service.AdminPageService;
import sheetplus.checkings.response.Api;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("private/admin")
public class AdminPageController {

    private final AdminPageService adminPageService;

    @GetMapping("/{contest}/home")
    public Api<AdminHomeResponseDto> readAdminHome(
            @PathVariable(name = "contest") Long contestId){
        AdminHomeResponseDto adminHomeResponseDto =
                adminPageService.adminHomeRead(contestId);
        return Api.READ(adminHomeResponseDto);
    }

    @GetMapping("/{contest}/eventManage/draw/member/list")
    public Api<List<MemberInfoDto>> readDrawMemberList(
            @PathVariable("contest") Long contestId){
        return Api.READ(adminPageService.readDrawMemberList(contestId));
    }


    @GetMapping("/{contest}/eventManage/prize/member/list")
    public Api<List<MemberInfoDto>> readPrizeMemberList(
            @PathVariable("contest") Long contestId){
        return Api.READ(adminPageService.readPrizeMemberList(contestId));
    }

}
