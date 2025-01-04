package sheetplus.checkings.business.page.superadmin.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sheetplus.checkings.domain.adminacceptcons.dto.AdminAcceptDto.AdminAcceptAndCreateRequestDto;
import sheetplus.checkings.domain.adminacceptcons.dto.AdminAcceptDto.AdminAcceptListResponseDto;
import sheetplus.checkings.domain.adminacceptcons.entity.AdminAcceptCons;
import sheetplus.checkings.domain.member.dto.MemberDto.MemberRequestDto;
import sheetplus.checkings.domain.member.dto.MemberDto.MemberInfoResponseDto;
import sheetplus.checkings.domain.member.entity.Member;
import sheetplus.checkings.domain.member.service.MemberCRUDService;
import sheetplus.checkings.business.page.superadmin.service.SuperAdminService;
import sheetplus.checkings.exception.error.ErrorResponse;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("private/super-admin/")
@Tag(name = "Super-Admin", description = "Super-Admin Service API")
public class SuperAdminController {

    private final SuperAdminService superAdminService;
    private final MemberCRUDService memberCRUDService;


    @GetMapping("admins/v1")
    @Operation(summary = "Admins GET", description = "모든 Admin Member를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "모든 Admin Member 조회 성공",
                    content = @Content(array = @ArraySchema(schema =
                    @Schema(implementation = AdminAcceptListResponseDto.class)),
                            mediaType = "application/json"),
                    headers = {@Header(name = "etag",
                            description = "\"etagexample\"과 같은 형태로 제공됩니다. If-None-Match속성에 Etag를 추가해서 요청하세요"),
                            @Header(name = "Cache-Control",
                                    description = "클라이언트 캐시 사용, 캐싱 최대유효시간 1시간, 유효시간 지난 후에는 반드시 서버로 재요청하세요")}),
            @ApiResponse(responseCode = "304", description = "캐시 데이터의 변경사항이 없습니다. 로컬 캐시 데이터를 사용하세요",
                    content = @Content (mediaType = "None")),
            @ApiResponse(responseCode = "400", description = "잘못된 HTTP 입력 요청",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class),
                            mediaType = "application/json")),
            @ApiResponse(responseCode = "401", description = "액세스 토큰이 없습니다",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class),
                            mediaType = "application/json")),
            @ApiResponse(responseCode = "403", description = "접근 권한이 없는 사용자입니다.",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class),
                            mediaType = "application/json"))
    })
    public ResponseEntity<List<AdminAcceptListResponseDto>> readAdminList(
            @Parameter(description = "조회할 페이지 번호", example = "1")
            @RequestParam(value = "offset", required = false)
            Integer offset,
            @Parameter(description = "페이지당 조회할 데이터 개수", example = "1")
            @RequestParam(value = "limit", required = false)
            Integer limit
    ){

        List<AdminAcceptCons> admins = superAdminService
                .readAdmins(PageRequest.of(offset-1, limit));

        return ResponseEntity.ok(
                admins.stream()
                        .map(p -> AdminAcceptListResponseDto.builder()
                                .email(p.getEmail())
                                .name(p.getName())
                                .major(p.getMajor())
                                .studentId(p.getStudentId())
                                .acceptCons(p.getAcceptCons().getMessage())
                                .build())
                        .toList()
        );
    }

    @PostMapping("admins/v1")
    @Operation(summary = "Admin Accept and CREATE ", description = "Admin Member를 승인하고, 새로운 Admin Member를 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Admin Member 승인 및 생성 성공",
                    content = @Content(schema =
                    @Schema(implementation = MemberInfoResponseDto.class),
                            mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "요청한 입력값이 지정된 검증을 실패했습니다.",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class),
                            mediaType = "application/json")),
            @ApiResponse(responseCode = "401", description = "액세스 토큰이 없습니다",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class),
                            mediaType = "application/json")),
            @ApiResponse(responseCode = "403", description = "접근 권한이 없는 사용자입니다.",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class),
                            mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "[Admin] 정보를 찾을 수 없습니다",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class),
                            mediaType = "application/json")),
            @ApiResponse(responseCode = "409", description = "이미 존재하는 [Member, Admin]입니다.",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class),
                            mediaType = "application/json"))
    })
    public ResponseEntity<MemberInfoResponseDto> updateAndCreateAdmin(
            @RequestBody AdminAcceptAndCreateRequestDto adminAcceptAndCreateRequestDto){
        MemberRequestDto memberRequestDto
                = superAdminService.updateAdminCons(adminAcceptAndCreateRequestDto.getEmail());

        Member member = memberCRUDService.createMember(memberRequestDto);
        return ResponseEntity.created(URI.create(""))
                .body(MemberInfoResponseDto.builder()
                        .id(member.getId())
                        .email(member.getUniversityEmail())
                        .memberType(member.getMemberType())
                        .major(member.getMajor())
                        .studentId(member.getStudentId())
                        .name(member.getName())
                        .build());
    }


    @DeleteMapping("admins/{admin}/v1")
    @Operation(summary = "Admin Delete", description = "Admin Member를 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Admin Member를 삭제했습니다."),
            @ApiResponse(responseCode = "400", description = "요청한 입력값이 지정된 검증을 실패했습니다.",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class),
                            mediaType = "application/json")),
            @ApiResponse(responseCode = "401", description = "액세스 토큰이 없습니다",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class),
                            mediaType = "application/json")),
            @ApiResponse(responseCode = "403", description = "접근 권한이 없는 사용자입니다.",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class),
                            mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "[Admin] 정보를 찾을 수 없습니다",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class),
                            mediaType = "application/json"))
    })
    public ResponseEntity<Void> deleteAdmin(
            @Parameter(description = "Admin Email", example = "1234@sch.ac.kr")
            @PathVariable(value = "admin") String email){
        superAdminService.deleteAdmin(email);

        return ResponseEntity.noContent().build();
    }

}
