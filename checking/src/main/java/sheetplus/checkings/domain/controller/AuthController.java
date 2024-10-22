package sheetplus.checkings.domain.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import sheetplus.checkings.domain.dto.LoginDto;
import sheetplus.checkings.domain.dto.MemberLoginRequestDto;
import sheetplus.checkings.domain.dto.TokenDto;
import sheetplus.checkings.domain.service.AuthService;
import sheetplus.checkings.response.Api;

@RequiredArgsConstructor
@RestController
@Slf4j
public class AuthController {
    public final AuthService authService;

    @PostMapping("public/refresh")
    public Api<TokenDto> refreshToken(@RequestHeader(value = "refresh-token"
            , required = false) String refreshToken){
        TokenDto tokenDto = authService.refreshTokens(refreshToken);
        return Api.OK(tokenDto);
    }

    @PostMapping("public/login")
    public Api<TokenDto> loginMember(
            @RequestBody MemberLoginRequestDto memberLoginRequestDto){

        TokenDto tokenDto = authService.memberLogin(LoginDto.builder()
                        .id(null)
                        .email(memberLoginRequestDto.getEmail())
                        .memberType(memberLoginRequestDto.getMemberType())
                .build());
        return Api.OK(tokenDto);
    }

}