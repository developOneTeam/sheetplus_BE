package sheetplus.checkings.business.page.common.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sheetplus.checkings.domain.contest.dto.ContestDto.ContestInfoResponseDto;
import sheetplus.checkings.business.page.common.service.CommonPageService;
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
            @ApiResponse(responseCode = "200", description = "이벤트 즐겨찾기 조회 성공",
                    content = @Content(array = @ArraySchema(schema =
                    @Schema(implementation = ContestInfoResponseDto.class)),
                            mediaType = "application/json")),
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

}
