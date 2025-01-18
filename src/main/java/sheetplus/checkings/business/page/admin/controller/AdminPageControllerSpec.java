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
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import sheetplus.checkings.business.page.admin.dto.AdminPageDto.*;
import sheetplus.checkings.domain.member.dto.MemberDto.MemberInfoResponseDto;
import sheetplus.checkings.exception.error.ErrorResponse;

import java.util.List;

@Tag(name = "Admin-Page", description = "Admin-Page Service API")
public interface AdminPageControllerSpec {

    @Operation(summary = "어드민 홈페이지 - 스탬프 통계", description = "Admin-Page Home화면의 스탬프 통계 데이터를 조회합니다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Admin-Page Home화면의 스탬프 통계 데이터 조회를 성공했습니다",
                    content = @Content(array = @ArraySchema(schema =
                    @Schema(implementation = AdminStampStatsDto.class)),
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
    ResponseEntity<AdminStampStatsDto> readAdminHomeStampStats(
            @Parameter(description = "Contest PK", example = "1")
            Long contestId);

    @Operation(summary = "어드민 홈페이지 - Contest 통계", description = "어드민 페이지 Home화면의 Contest 통계 데이터를 조회합니다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "어드민 페이지 Home화면의 Contest 통계 데이터 조회를 성공했습니다",
                    content = @Content(array = @ArraySchema(schema =
                    @Schema(implementation = AdminContestStatsDto.class)),
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
    ResponseEntity<AdminContestStatsDto> readAdminHomeContestStats(
            @Parameter(description = "Contest PK", example = "1")
            Long contestId);

    @Operation(summary = "어드민 홈페이지 - Event 통계", description = "어드민 페이지 Home화면의 Event 통계 데이터를 조회합니다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "어드민 페이지 Home화면의 Event 통계 데이터 조회를 성공했습니다",
                    content = @Content(array = @ArraySchema(schema =
                    @Schema(implementation = AdminEventStatsDto.class)),
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
    ResponseEntity<AdminEventStatsDto> readAdminHomeEventStats(
            @Parameter(description = "Contest PK", example = "1")
            Long contestId,
            @NotNull(message = "offset의 null값은 허용하지 않습니다.")
            @Parameter(description = "조회할 페이지 번호", example = "1")
            Integer offset,
            @NotNull(message = "limit의 null값은 허용하지 않습니다.")
            @Parameter(description = "페이지당 조회할 데이터 개수", example = "1")
            Integer limit);

    @Operation(summary = "어드민 홈페이지 - Entry 통계", description = "어드민 홈페이지 - Entry 통계 데이터를 조회합니다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "어드민 홈페이지 - Entry 통계 데이터 조회를 성공했습니다",
                    content = @Content(array = @ArraySchema(schema =
                    @Schema(implementation = AdminEntryStatsDto.class)),
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
    ResponseEntity<AdminEntryStatsDto> readAdminHomeEntryStats(
            @Parameter(description = "Contest PK", example = "1")
            Long contestId,
            @NotNull(message = "offset의 null값은 허용하지 않습니다.")
            @Parameter(description = "조회할 페이지 번호", example = "1")
            Integer offset,
            @NotNull(message = "limit의 null값은 허용하지 않습니다.")
            @Parameter(description = "페이지당 조회할 데이터 개수", example = "1")
            Integer limit);

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
    ResponseEntity<List<MemberInfoResponseDto>> readDrawMemberList(
            @Parameter(description = "Contest PK", example = "1")
            Long contestId,
            @NotNull(message = "offset의 null값은 허용하지 않습니다.")
            @Parameter(description = "조회할 페이지 번호", example = "1")
            Integer offset,
            @NotNull(message = "limit의 null값은 허용하지 않습니다.")
            @Parameter(description = "페이지당 조회할 데이터 개수", example = "1")
            Integer limit);


    @Operation(summary = "Admin-Page ContestInfo with Counts", description = "Admin-Page Contest 정보 조회 화면")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Contest 정보 조회를 성공했습니다.",
                    content = @Content(array = @ArraySchema(schema =
                    @Schema(implementation = MemberInfoResponseDto.class)),
                            mediaType = "application/json"),
                    headers = {@Header(name = "etag",
                            description = "\"etagexample\"과 같은 형태로 제공됩니다. If-None-Match속성에 Etag를 추가해서 요청하세요"),
                            @Header(name = "Cache-Control",
                                    description = "클라이언트 캐시 사용, 캐싱 최대유효시간 1시간, 유효시간 지난 후에는 반드시 서버로 재요청하세요")}),
            @ApiResponse(responseCode = "304", description = "캐시 데이터의 변경사항이 없습니다. 로컬 캐시 데이터를 사용하세요",
                    content = @Content (mediaType = "None")),
            @ApiResponse(responseCode = "401", description = "액세스 토큰이 없습니다",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class),
                            mediaType = "application/json")),
            @ApiResponse(responseCode = "403", description = "접근 권한이 없는 사용자입니다.",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class),
                            mediaType = "application/json"))
    })
    ResponseEntity<List<ContestInfoWithCounts>> readContestInfos();
}
