package sheetplus.checkings.domain.member.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import sheetplus.checkings.business.auth.dto.LoginDto;
import sheetplus.checkings.domain.member.dto.MemberDto.MemberRequestDto;
import sheetplus.checkings.domain.member.dto.MemberDto.MemberUpdateRequestDto;
import sheetplus.checkings.domain.token.dto.TokenDto;
import sheetplus.checkings.domain.member.entity.Member;
import sheetplus.checkings.business.auth.service.AuthService;
import sheetplus.checkings.business.email.service.EmailService;
import sheetplus.checkings.domain.member.service.MemberCRUDService;
import sheetplus.checkings.business.page.superadmin.service.SuperAdminService;
import sheetplus.checkings.exception.error.ErrorResponse;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Member", description = "Member CUD API")
public class MemberController {

    private final MemberCRUDService memberCRUDService;
    private final AuthService authService;
    private final SuperAdminService superAdminService;
    private final EmailService emailService;

    @PostMapping("public/member/v1")
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
                    description = "이미 회원가입한 멤버입니다, 가입 등록한 ADMIN 계정이 존재합니다, 요청한 멤버의 타입과 회원가입한 멤버의 타입이 일치하지 않습니다, 이메일이 일치하지 않습니다.",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class),
                            mediaType = "application/json"))
    })
    public ResponseEntity<TokenDto> createMember(
            @RequestBody @Validated MemberRequestDto memberRequestDto){

        emailService.verifyEmail(memberRequestDto.getUniversityEmail(),
                memberRequestDto.getCode());

        // 멤버 타입 체크 로직
        if(!authService.memberTypeCheck(memberRequestDto.getMemberType())){
            return ResponseEntity.created(URI.create(""))
                    .body(superAdminService.createAdmin(memberRequestDto));
        }


        Member member = memberCRUDService.createMember(memberRequestDto);
        TokenDto tokenWithData = authService.memberLogin(LoginDto.builder()
                        .id(member.getId())
                        .email(member.getUniversityEmail())
                        .memberType(member.getMemberType())
                .build());

        return ResponseEntity.created(URI.create(""))
                .body(tokenWithData);
    }



    @PutMapping("private/member/v1")
    @Operation(summary = "Member UPDATE", description = "Member 데이터를 업데이트합니다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Member 데이터를 업데이트했습니다.",
                    content = @Content(schema = @Schema(implementation = MemberUpdateRequestDto.class),
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
    public ResponseEntity<MemberUpdateRequestDto> updateMember(
            @Parameter(description = "액세스 토큰입니다 Header에 포함해서 요청하세요", hidden = true)
            @RequestHeader(value = "Authorization", required = false) String token,
            @RequestBody @Validated MemberUpdateRequestDto memberUpdateRequestDto
    ){

        MemberUpdateRequestDto updatedMember
                = memberCRUDService.updateMember(memberUpdateRequestDto,
                token.replace("Bearer ", ""));

        return ResponseEntity.ok(updatedMember);
    }


    @DeleteMapping("private/member/v1")
    @Operation(summary = "Member Delete", description = "Member 데이터를 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Member 데이터를 삭제했습니다."),
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
    public ResponseEntity<Void> deleteMember(
            @Parameter(description = "액세스 토큰입니다 Header에 포함해서 요청하세요", hidden = true)
            @RequestHeader(value = "Authorization", required = false) String token){
        memberCRUDService.deleteMember(token.replace("Bearer ", ""));
        return ResponseEntity.noContent().build();
    }

}
