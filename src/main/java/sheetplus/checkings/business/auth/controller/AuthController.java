package sheetplus.checkings.business.auth.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import sheetplus.checkings.business.auth.dto.LoginDto;
import sheetplus.checkings.domain.member.dto.MemberDto.MemberLoginRequestDto;
import sheetplus.checkings.domain.token.dto.TokenDto;
import sheetplus.checkings.business.auth.service.AuthService;
import sheetplus.checkings.business.email.service.EmailService;
import sheetplus.checkings.exception.error.ErrorResponse;

import java.net.URI;

@RequiredArgsConstructor
@RestController
@RequestMapping("public/auth")
@Slf4j
@Tag(name = "Authentication", description = "Authentication Service API")
public class AuthController {
    public final AuthService authService;
    private final EmailService emailService;

    @PostMapping("/token/refresh/v1")
    @Operation(summary = "Access-Token refresh with Refresh-Token",
            description = "Access-Token과 Refresh-Token을 재발급합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "토큰 생성"
                    , content = {@Content(schema = @Schema(implementation = TokenDto.class),
                    mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "유효하지 않은 토큰이거나 찾을 수 없습니다.",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class),
                            mediaType = "application/json"))
    })
    public ResponseEntity<TokenDto> refreshToken(
            @Parameter(description = "리프래쉬 토큰입니다 Header에 포함해서 요청하세요", hidden = true)
            @RequestHeader(value = "refreshToken"
                    , required = false) String refreshToken){
        log.info("{}", refreshToken);

        TokenDto tokenDto = authService.refreshTokens(refreshToken);
        return ResponseEntity.created(URI.create(""))
                .body(tokenDto);
    }

    @PostMapping("/login/v1")
    @Operation(summary = "Member login", description = "학교이메일로 Member 로그인을 진행합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = TokenDto.class),
                    mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "요청한 입력값이 지정된 검증을 실패했습니다.",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class),
                            mediaType = "application/json")),
            @ApiResponse(responseCode = "401", description = "인증된 인증코드가 아닙니다.",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class),
                            mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "임시멤버정보를 찾을 수 없습니다",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class),
                            mediaType = "application/json")),
            @ApiResponse(responseCode = "409", description = "요청한 멤버 타입과 회원가입한 멤버의 타입이 일치하지 않습니다.",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class),
                            mediaType = "application/json"))
    })
    public ResponseEntity<TokenDto> loginMember(
            @RequestBody @Validated MemberLoginRequestDto memberLoginRequestDto){

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