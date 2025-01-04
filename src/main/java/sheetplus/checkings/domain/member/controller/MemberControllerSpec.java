package sheetplus.checkings.domain.member.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import sheetplus.checkings.domain.member.dto.MemberDto;
import sheetplus.checkings.domain.token.dto.TokenDto;
import sheetplus.checkings.exception.error.ErrorResponse;

@Tag(name = "Member", description = "Member CUD API")
public interface MemberControllerSpec {


    @Operation(summary = "Member CREATE", description = "Member를 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Member를 생성했습니다",
                    content = @Content(schema = @Schema(implementation = TokenDto.class),
                            mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "요청한 입력값이 지정된 검증을 실패했습니다.",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class),
                            mediaType = "application/json")),
            @ApiResponse(responseCode = "401", description = "인증된 인증코드가 아닙니다.",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class),
                            mediaType = "application/json")),
            @ApiResponse(responseCode = "403", description = "SUPER_ADMIN 회원가입은 개발자에게 요청하세요",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class),
                            mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "[TemporaryMember, Member] 정보를 찾을 수 없습니다",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class),
                            mediaType = "application/json")),
            @ApiResponse(responseCode = "409",
                    description = "이미 회원가입한 멤버입니다<br> 가입 등록한 ADMIN 계정이 존재합니다<br> 요청한 멤버의 타입과 회원가입한 멤버의 타입이 일치하지 않습니다<br> 이메일이 일치하지 않습니다<br>",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class),
                            mediaType = "application/json"))
    })
    ResponseEntity<TokenDto> createMember(
            @RequestBody @Validated MemberDto.MemberRequestDto memberRequestDto);

    @Operation(summary = "Member UPDATE", description = "Member 데이터를 업데이트합니다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Member 데이터를 업데이트했습니다.",
                    content = @Content(schema = @Schema(implementation = MemberDto.MemberUpdateRequestDto.class),
                            mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "요청한 입력값이 지정된 검증을 실패했습니다.",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class),
                            mediaType = "application/json")),
            @ApiResponse(responseCode = "401", description = "액세스 토큰이 없습니다",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class),
                            mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "[Member] 정보를 찾을 수 없습니다",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class),
                            mediaType = "application/json"))
    })
    ResponseEntity<MemberDto.MemberUpdateRequestDto> updateMember(
            @Parameter(description = "액세스 토큰입니다 Header에 포함해서 요청하세요", hidden = true)
            @RequestHeader(value = "Authorization", required = false) String token,
            @RequestBody @Validated MemberDto.MemberUpdateRequestDto memberUpdateRequestDto
    );

    @Operation(summary = "Member Delete", description = "Member 데이터를 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Member 데이터를 삭제했습니다.",
                    content = @Content(mediaType = "None")),
            @ApiResponse(responseCode = "400", description = "요청한 입력값이 지정된 검증을 실패했습니다.",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class),
                            mediaType = "application/json")),
            @ApiResponse(responseCode = "401", description = "액세스 토큰이 없습니다",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class),
                            mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "[Member] 정보를 찾을 수 없습니다",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class),
                            mediaType = "application/json"))
    })
    ResponseEntity<Void> deleteMember(
            @Parameter(description = "액세스 토큰입니다 Header에 포함해서 요청하세요", hidden = true)
            @RequestHeader(value = "Authorization", required = false) String token);

}
