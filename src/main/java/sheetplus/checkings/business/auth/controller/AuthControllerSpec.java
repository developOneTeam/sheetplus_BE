package sheetplus.checkings.business.auth.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import sheetplus.checkings.domain.member.dto.MemberDto;
import sheetplus.checkings.domain.token.dto.TokenDto;
import sheetplus.checkings.exception.error.ErrorResponse;

@Tag(name = "Authentication", description = "Authentication Service API")
public interface AuthControllerSpec {

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
    ResponseEntity<TokenDto> refreshToken(
            @Parameter(description = "리프래쉬 토큰입니다 Header에 포함해서 요청하세요", hidden = true)
            String refreshToken);

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
    ResponseEntity<TokenDto> loginMember(MemberDto.MemberLoginRequestDto memberLoginRequestDto);
}
