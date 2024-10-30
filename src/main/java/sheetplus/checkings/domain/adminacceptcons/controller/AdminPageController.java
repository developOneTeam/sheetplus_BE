package sheetplus.checkings.domain.adminacceptcons.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import sheetplus.checkings.business.page.admin.dto.AdminHomeResponseDto;
import sheetplus.checkings.domain.member.dto.response.MemberInfoResponseDto;
import sheetplus.checkings.business.page.admin.service.AdminPageService;
import sheetplus.checkings.util.response.Api;

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
    public Api<List<MemberInfoResponseDto>> readDrawMemberList(
            @PathVariable("contest") Long contestId){
        return Api.READ(adminPageService.readDrawMemberList(contestId));
    }


    /**
     *
     * Deprecated
     * 사유: 증정 기능 비즈니스 정책상 사용 보류
     *
     */
    //@GetMapping("/{contest}/eventManage/prize/member/list")
    public Api<List<MemberInfoResponseDto>> readPrizeMemberList(
            @PathVariable("contest") Long contestId){
        return Api.READ(adminPageService.readPrizeMemberList(contestId));
    }

}
