package sheetplus.checkings.business.page.common.controller;


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
import sheetplus.checkings.domain.contest.dto.ContestDto.ContestInfoResponseDto;
import sheetplus.checkings.business.page.common.service.CommonPageService;
import sheetplus.checkings.domain.event.dto.EventDto.EventResponseDto;
import sheetplus.checkings.exception.error.ErrorResponse;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("public/")
@Tag(name = "CommonPage", description = "CommonPage Service API")
public class CommonPageController {

    private final CommonPageService commonPageService;

    @GetMapping("contests/v1")
    @Operation(summary = "CommonPage GET", description = "모든 Contest를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "모든 Contest 데이터 조회 성공",
                    content = @Content(array = @ArraySchema(schema =
                    @Schema(implementation = ContestInfoResponseDto.class)),
                            mediaType = "application/json"),
                    headers = {@Header(name = "etag",
                    description = "\"etagexample\"과 같은 형태로 제공됩니다. If-None-Match속성에 Etag를 추가해서 요청하세요"),
                    @Header(name = "Cache-Control",
                            description = "클라이언트 캐시 사용, 캐싱 최대유효시간 1시간, 유효시간 지난 후에는 반드시 서버로 재요청하세요")}),
            @ApiResponse(responseCode = "304", description = "캐시 데이터의 변경사항이 없습니다. 로컬 캐시 데이터를 사용하세요",
                    content = @Content (mediaType = "None")),
            @ApiResponse(responseCode = "400", description = "잘못된 HTTP 입력 요청",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class),
                            mediaType = "application/json"))
    })
    public ResponseEntity<List<ContestInfoResponseDto>> readContestInfo(
            @Parameter(description = "조회할 페이지 번호", example = "1")
            @RequestParam(value = "offset", required = false)
            Integer offset,
            @Parameter(description = "페이지당 조회할 데이터 개수", example = "1")
            @RequestParam(value = "limit", required = false)
            Integer limit
    ){
        return ResponseEntity.ok(commonPageService
                .readContestInfo(PageRequest.of(offset-1, limit)));
    }

    @GetMapping("public/contests/{contest}/schedules/v1")
    @Operation(summary = "Schedule Page GET", description = "일정 페이지 데이터를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "일정 페이지 데이터 조회 성공",
                    content = @Content(array = @ArraySchema(schema =
                    @Schema(implementation = EventResponseDto.class)),
                            mediaType = "application/json"),
                    headers = {@Header(name = "etag",
                            description = "\"etagexample\"과 같은 형태로 제공됩니다. If-None-Match속성에 Etag를 추가해서 요청하세요"),
                            @Header(name = "Cache-Control",
                                    description = "클라이언트 캐시 사용, 캐싱 최대유효시간 1시간, 유효시간 지난 후에는 반드시 서버로 재요청하세요")}),
            @ApiResponse(responseCode = "304", description = "캐시 데이터의 변경사항이 없습니다. 로컬 캐시 데이터를 사용하세요",
                    content = @Content (mediaType = "None")),
            @ApiResponse(responseCode = "400", description = "잘못된 HTTP 입력 요청",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class),
                            mediaType = "application/json"))
    })
    public ResponseEntity<List<EventResponseDto>> readStudentSchedule(
            @Parameter(description = "Contest PK", example = "1")
            @PathVariable("contest") Long contestId,

            @Parameter(description = "조회할 페이지 번호", example = "1")
            @RequestParam(value = "offset", required = false)
            Integer offset,

            @Parameter(description = "페이지당 조회할 데이터 개수", example = "1")
            @RequestParam(value = "limit", required = false)
            Integer limit
    ){
        return ResponseEntity.ok(commonPageService
                .readStudentSchedulePage(contestId, PageRequest.of(offset-1, limit)));
    }

}
