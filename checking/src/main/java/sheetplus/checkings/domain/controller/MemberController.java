package sheetplus.checkings.domain.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import sheetplus.checkings.domain.dto.LoginDto;
import sheetplus.checkings.domain.dto.MemberRequestDto;
import sheetplus.checkings.domain.dto.MemberUpdateRequestDto;
import sheetplus.checkings.domain.dto.TokenDto;
import sheetplus.checkings.domain.entity.Member;
import sheetplus.checkings.domain.service.AuthService;
import sheetplus.checkings.domain.service.MemberCRUDService;
import sheetplus.checkings.response.Api;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberCRUDService memberCRUDService;
    private final AuthService authService;

    @PostMapping("public/register")
    public Api<TokenDto> createMember(
            @RequestBody MemberRequestDto memberRequestDto){
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
