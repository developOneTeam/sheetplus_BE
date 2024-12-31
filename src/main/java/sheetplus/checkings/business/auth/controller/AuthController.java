package sheetplus.checkings.business.auth.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sheetplus.checkings.business.auth.dto.LoginDto;
import sheetplus.checkings.domain.member.dto.MemberDto.MemberLoginRequestDto;
import sheetplus.checkings.domain.token.dto.TokenDto;
import sheetplus.checkings.business.auth.service.AuthService;
import sheetplus.checkings.business.email.service.EmailService;

@RequiredArgsConstructor
@RestController
@RequestMapping("public/auth")
@Slf4j
public class AuthController {
    public final AuthService authService;
    private final EmailService emailService;

    @PostMapping("/token/refresh/v1")
    public ResponseEntity<TokenDto> refreshToken(
            @RequestHeader(value = "refreshToken"
                    , required = false) String refreshToken){
        log.info("{}", refreshToken);

        TokenDto tokenDto = authService.refreshTokens(refreshToken);
        return ResponseEntity.ok(tokenDto);
    }

    @PostMapping("/login/v1")
    public ResponseEntity<TokenDto> loginMember(
            @RequestBody MemberLoginRequestDto memberLoginRequestDto){

        emailService.verifyEmail(memberLoginRequestDto.getEmail(),
                memberLoginRequestDto.getCode());

        TokenDto tokenDto = authService.memberLogin(LoginDto.builder()
                        .id(null)
                        .email(memberLoginRequestDto.getEmail())
                        .memberType(memberLoginRequestDto.getMemberType())
                .build());
        return ResponseEntity.ok(tokenDto);
    }

}