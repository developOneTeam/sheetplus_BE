package sheetplus.checkings.business.auth.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import sheetplus.checkings.business.auth.dto.LoginDto;
import sheetplus.checkings.domain.member.dto.request.MemberLoginRequestDto;
import sheetplus.checkings.domain.token.dto.TokenDto;
import sheetplus.checkings.business.auth.service.AuthService;
import sheetplus.checkings.business.email.service.EmailService;
import sheetplus.checkings.util.response.Api;

@RequiredArgsConstructor
@RestController
@Slf4j
public class AuthController {
    public final AuthService authService;
    private final EmailService emailService;

    @PostMapping("public/refresh")
    public Api<TokenDto> refreshToken(
            @RequestHeader(value = "refreshToken"
                    , required = false) String refreshToken){
        log.info("{}", refreshToken);

        TokenDto tokenDto = authService.refreshTokens(refreshToken);
        return Api.OK(tokenDto);
    }

    @PostMapping("public/login")
    public Api<TokenDto> loginMember(
            @RequestBody MemberLoginRequestDto memberLoginRequestDto){

        emailService.verifyEmail(memberLoginRequestDto.getEmail(),
                memberLoginRequestDto.getCode());

        TokenDto tokenDto = authService.memberLogin(LoginDto.builder()
                        .id(null)
                        .email(memberLoginRequestDto.getEmail())
                        .memberType(memberLoginRequestDto.getMemberType())
                .build());
        return Api.OK(tokenDto);
    }

}