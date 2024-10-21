package sheetplus.checkings.domain.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import sheetplus.checkings.domain.dto.*;
import sheetplus.checkings.domain.entity.AdminAcceptCons;
import sheetplus.checkings.domain.entity.Member;
import sheetplus.checkings.domain.entity.enums.MemberType;
import sheetplus.checkings.domain.service.AuthService;
import sheetplus.checkings.domain.service.MemberCRUDService;
import sheetplus.checkings.domain.service.SuperAdminService;
import sheetplus.checkings.response.Api;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("private/super/admin/")
public class SuperAdminController {

    private final SuperAdminService superAdminService;
    private final MemberCRUDService memberCRUDService;
    private final AuthService authService;


    @GetMapping("acceptable/admin/read")
    public Api<List<AdminAcceptListResponseDto>> readAdminList(){

        List<AdminAcceptCons> admins = superAdminService.readAdmins();

        return Api.READ(
                admins.stream()
                        .map(p -> AdminAcceptListResponseDto.builder()
                                .email(p.getEmail())
                                .name(p.getName())
                                .major(p.getMajor())
                                .studentId(p.getStudentId())
                                .acceptCons(p.getAcceptCons().getMessage())
                                .build())
                        .toList()
        );
    }

    @PostMapping("acceptable/admin/create")
    public Api<MemberInfoDto> updateAndCreateAdmin(
            @RequestBody AdminAcceptAndCreateRequestDto adminAcceptAndCreateRequestDto){
        MemberRequestDto memberRequestDto
                = superAdminService.updateAdminCons(adminAcceptAndCreateRequestDto.getEmail());

        Member member = memberCRUDService.createMember(memberRequestDto);
        return Api.CREATED(
                MemberInfoDto.builder()
                        .id(member.getId())
                        .name(member.getName())
                        .major(member.getMajor())
                        .studentId(member.getStudentId())
                        .email(member.getUniversityEmail())
                        .memberType(MemberType.ADMIN)
                        .build());
    }


    @DeleteMapping("acceptable/admin/{admin}/delete")
    public Api<String> deleteAdmin(
            @PathVariable("admin") String email){
        superAdminService.deleteAdmin(email);

        return Api.DELETE("삭제완료");
    }

}
