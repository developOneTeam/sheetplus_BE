package sheetplus.checkings.domain.contest.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import sheetplus.checkings.domain.contest.dto.ContestDto.ContestRequestDto;
import sheetplus.checkings.domain.contest.dto.ContestDto.ContestResponseDto;
import sheetplus.checkings.exception.error.ErrorResponse;

@Tag(name = "Contest", description = "Contest CUD API")
public interface ContestControllerSpec {

    @Operation(summary = "Contest CREATE", description = "Contest를 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Contest를 생성했습니다",
                    content = @Content(schema = @Schema(implementation = ContestResponseDto.class),
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
            @ApiResponse(responseCode = "409", description = "시작시간이 종료시간보다 뒤에 있습니다.",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class),
                            mediaType = "application/json"))
    })
    ResponseEntity<ContestResponseDto> createContest(
            ContestRequestDto contestRequestDto);


    @Operation(summary = "Contest UPDATE", description = "Contest 데이터를 업데이트합니다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Contest 데이터를 업데이트했습니다.",
                    content = @Content(schema = @Schema(implementation = ContestResponseDto.class),
                            mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "잘못된 HTTP 입력 요청<br> 요청한 입력값이 지정된 검증을 실패했습니다.",
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
            @ApiResponse(responseCode = "409", description = "시작시간이 종료시간보다 뒤에 있습니다.",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class),
                            mediaType = "application/json"))
    })
    ResponseEntity<ContestResponseDto> updateContest(
            @Parameter(description = "Contest PK", example = "1")
            Long contestId,
            ContestRequestDto contestRequestDto
    );

    @Operation(summary = "Contest DELETE", description = "Contest를 삭제했습니다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Contest를 삭제했습니다.",
                    content = @Content(mediaType = "None")),
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
                            mediaType = "application/json"))
    })
    ResponseEntity<Void> deleteContest(
            @Parameter(description = "Contest PK", example = "1")
            Long contestId
    );

}
