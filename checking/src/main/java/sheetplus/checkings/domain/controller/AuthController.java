package sheetplus.checkings.domain.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import sheetplus.checkings.domain.dto.LoginDto;
import sheetplus.checkings.domain.dto.MemberLoginRequestDto;
import sheetplus.checkings.domain.dto.TokenDto;
import sheetplus.checkings.domain.service.AuthService;
import sheetplus.checkings.domain.service.EmailService;
import sheetplus.checkings.response.Api;

@RequiredArgsConstructor
@RestController
@Slf4j
public class AuthController {
    public final AuthService authService;
    private final EmailService emailService;

    @PostMapping("public/refresh")
    public Api<TokenDto> refreshToken(
            @CookieValue(value = "refreshToken"
            , required = false) String refreshToken,
                                      HttpServletResponse response){
        log.info("{}", refreshToken);

        TokenDto tokenDto = authService.refreshTokens(refreshToken, response);
        return Api.OK(tokenDto);
    }

    @PostMapping("public/login")
    public Api<TokenDto> loginMember(
            @RequestBody MemberLoginRequestDto memberLoginRequestDto,
            HttpServletResponse response){

        emailService.verifyEmail(memberLoginRequestDto.getEmail(),
                memberLoginRequestDto.getCode());

        TokenDto tokenDto = authService.memberLogin(LoginDto.builder()
                        .id(null)
                        .email(memberLoginRequestDto.getEmail())
                        .memberType(memberLoginRequestDto.getMemberType())
                .build(), response);
        return Api.OK(tokenDto);
    }

}