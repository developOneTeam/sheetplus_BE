package sheetplus.checking.domain.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import sheetplus.checking.domain.dto.TokenDto;
import sheetplus.checking.domain.service.AuthService;
import sheetplus.checking.response.Api;

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

}