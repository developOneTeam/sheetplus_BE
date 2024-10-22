package sheetplus.checkings.domain.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import sheetplus.checkings.domain.dto.LoginDto;
import sheetplus.checkings.domain.dto.MemberRequestDto;
import sheetplus.checkings.domain.dto.MemberUpdateRequestDto;
import sheetplus.checkings.domain.dto.TokenDto;
import sheetplus.checkings.domain.entity.Member;
import sheetplus.checkings.domain.service.AuthService;
import sheetplus.checkings.domain.service.EmailService;
import sheetplus.checkings.domain.service.MemberCRUDService;
import sheetplus.checkings.domain.service.SuperAdminService;
import sheetplus.checkings.response.Api;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberCRUDService memberCRUDService;
    private final AuthService authService;
    private final SuperAdminService superAdminService;
    private final EmailService emailService;

    @PostMapping("public/register")
    public Api<TokenDto> createMember(
            @RequestBody MemberRequestDto memberRequestDto){

        emailService.verifyEmail(memberRequestDto.getUniversityEmail(),
                memberRequestDto.getCode());

        // 멤버 존재여부 확인, 있으면 로그인 진행


        // 멤버 타입 체크 로직
        if(!authService.memberTypeCheck(memberRequestDto.getMemberType())){
            return Api.CREATED(superAdminService.createAdmin(memberRequestDto));
        }


        Member member = memberCRUDService.createMember(memberRequestDto);
        TokenDto tokenWithData = authService.memberLogin(LoginDto.builder()
                        .id(member.getId())
                        .email(member.getUniversityEmail())
                        .memberType(member.getMemberType())
                .build());

        return Api.CREATED(tokenWithData);
    }



    @PatchMapping("private/update")
    public Api<MemberUpdateRequestDto> updateMember(
            @RequestHeader(value = "Authorization", required = false) String token,
            @RequestBody MemberUpdateRequestDto memberUpdateRequestDto
    ){

        MemberUpdateRequestDto updatedMember
                = memberCRUDService.updateMember(memberUpdateRequestDto,
                token.replace("Bearer ", ""));

        return Api.UPDATED(updatedMember);
    }


    @DeleteMapping("private/delete")
    public Api<String> deleteMember(
            @RequestHeader(value = "Authorization", required = false) String token){
        memberCRUDService.deleteMember(token.replace("Bearer ", ""));
        return Api.DELETE("삭제 완료");
    }

}
