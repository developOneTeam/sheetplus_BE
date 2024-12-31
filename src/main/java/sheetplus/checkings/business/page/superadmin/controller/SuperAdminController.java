package sheetplus.checkings.business.page.superadmin.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sheetplus.checkings.domain.adminacceptcons.dto.request.AdminAcceptAndCreateRequestDto;
import sheetplus.checkings.domain.adminacceptcons.dto.response.AdminAcceptListResponseDto;
import sheetplus.checkings.domain.adminacceptcons.entity.AdminAcceptCons;
import sheetplus.checkings.domain.member.dto.request.MemberRequestDto;
import sheetplus.checkings.domain.member.dto.response.MemberInfoResponseDto;
import sheetplus.checkings.domain.member.entity.Member;
import sheetplus.checkings.business.auth.service.AuthService;
import sheetplus.checkings.domain.member.service.MemberCRUDService;
import sheetplus.checkings.business.page.superadmin.service.SuperAdminService;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("private/super-admin/")
public class SuperAdminController {

    private final SuperAdminService superAdminService;
    private final MemberCRUDService memberCRUDService;


    @GetMapping("admins/v1")
    public ResponseEntity<List<AdminAcceptListResponseDto>> readAdminList(){

        List<AdminAcceptCons> admins = superAdminService.readAdmins();

        return ResponseEntity.ok(
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

    @PostMapping("admins/v1")
    public ResponseEntity<MemberInfoResponseDto> updateAndCreateAdmin(
            @RequestBody AdminAcceptAndCreateRequestDto adminAcceptAndCreateRequestDto){
        MemberRequestDto memberRequestDto
                = superAdminService.updateAdminCons(adminAcceptAndCreateRequestDto.getEmail());

        Member member = memberCRUDService.createMember(memberRequestDto);
        return ResponseEntity.created(URI.create(""))
                .body(MemberInfoResponseDto.builder()
                        .id(member.getId())
                        .email(member.getUniversityEmail())
                        .memberType(member.getMemberType())
                        .major(member.getMajor())
                        .studentId(member.getStudentId())
                        .name(member.getName())
                        .build());
    }


    @DeleteMapping("admins/{admin}/v1")
    public ResponseEntity<String> deleteAdmin(
            @PathVariable("admin") String email){
        superAdminService.deleteAdmin(email);

        return ResponseEntity.noContent().build();
    }

}
