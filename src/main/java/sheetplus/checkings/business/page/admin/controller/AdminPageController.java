package sheetplus.checkings.business.page.admin.controller;


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
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sheetplus.checkings.business.page.admin.dto.AdminPageDto.AdminHomeResponseDto;
import sheetplus.checkings.domain.favorite.dto.FavoriteDto.FavoriteResponseDto;
import sheetplus.checkings.domain.member.dto.MemberDto.MemberInfoResponseDto;
import sheetplus.checkings.business.page.admin.service.AdminPageService;
import sheetplus.checkings.exception.error.ErrorResponse;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("private/admin")
@Tag(name = "Admin-Page", description = "Admin-Page Service API")
public class AdminPageController {

    private final AdminPageService adminPageService;

    @GetMapping("/contests/{contest}/home/v1")
    @Operation(summary = "Admin-Page Home", description = "Admin-Page Home화면 데이터를 조회합니다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Admin-Page Home화면 조회를 성공했습니다",
                    content = @Content(array = @ArraySchema(schema =
                    @Schema(implementation = FavoriteResponseDto.class)),
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
                            mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "[Contest] 정보를 찾을 수 없습니다",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class),
                            mediaType = "application/json")),
    })
    public ResponseEntity<AdminHomeResponseDto> readAdminHome(
            @Parameter(description = "Contest PK", example = "1")
            @PathVariable(name = "contest") Long contestId){
        AdminHomeResponseDto adminHomeResponseDto =
                adminPageService.adminHomeRead(contestId);
        return ResponseEntity.ok(adminHomeResponseDto);
    }


    @GetMapping("/contests/{contest}/draw/members/v1")
    @Operation(summary = "Admin-Page Draw-Member", description = "Admin-Page Draw-Member 조회 화면")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Draw-Member 조회를 성공했습니다.",
                    content = @Content(array = @ArraySchema(schema =
                    @Schema(implementation = MemberInfoResponseDto.class)),
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
                            mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "[Contest] 정보를 찾을 수 없습니다",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class),
                            mediaType = "application/json")),
    })
    public ResponseEntity<List<MemberInfoResponseDto>> readDrawMemberList(
            @Parameter(description = "Contest PK", example = "1")
            @PathVariable("contest") Long contestId,

            @Parameter(description = "조회할 페이지 번호", example = "1")
            @RequestParam(value = "offset", required = false)
            Integer offset,

            @Parameter(description = "페이지당 조회할 데이터 개수", example = "1")
            @RequestParam(value = "limit", required = false)
            Integer limit
    ){
        return ResponseEntity.ok(adminPageService
                .readDrawMemberList(contestId, PageRequest.of(offset-1, limit)));
    }

    /**
     *
     * @Deprecated 사유: 증정 기능 비즈니스 정책상 사용 보류
     *
     */
    //@GetMapping("/{contest}/eventManage/prize/member/list")
    public ResponseEntity<List<MemberInfoResponseDto>> readPrizeMemberList(
            @PathVariable("contest") Long contestId){
        return ResponseEntity.ok(adminPageService.readPrizeMemberList(contestId));
    }

}
