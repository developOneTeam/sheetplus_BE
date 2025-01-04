package sheetplus.checkings.business.page.admin.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sheetplus.checkings.business.page.admin.dto.AdminPageDto.AdminHomeResponseDto;
import sheetplus.checkings.domain.member.dto.MemberDto.MemberInfoResponseDto;
import sheetplus.checkings.business.page.admin.service.AdminPageService;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("private/admin")
public class AdminPageController implements AdminPageControllerSpec{

    private final AdminPageService adminPageService;

    @GetMapping("/contests/{contest}/home/v1")
    public ResponseEntity<AdminHomeResponseDto> readAdminHome(
            @PathVariable(name = "contest") Long contestId){
        AdminHomeResponseDto adminHomeResponseDto =
                adminPageService.adminHomeRead(contestId);
        return ResponseEntity.ok(adminHomeResponseDto);
    }


    @GetMapping("/contests/{contest}/draw/members/v1")
    public ResponseEntity<List<MemberInfoResponseDto>> readDrawMemberList(
            @PathVariable("contest") Long contestId,
            @RequestParam(value = "offset", required = false)
            Integer offset,
            @RequestParam(value = "limit", required = false)
            Integer limit
    ){
        return ResponseEntity.ok(adminPageService
                .readDrawMemberList(contestId, PageRequest.of(offset-1, limit)));
    }

    /**
     *
     * @Deprecated 사유: 증정 기능 비즈니스 정책상 사용 보류
     *
     */
    //@GetMapping("/{contest}/eventManage/prize/member/list")
    public ResponseEntity<List<MemberInfoResponseDto>> readPrizeMemberList(
            @PathVariable("contest") Long contestId){
        return ResponseEntity.ok(adminPageService.readPrizeMemberList(contestId));
    }

}
