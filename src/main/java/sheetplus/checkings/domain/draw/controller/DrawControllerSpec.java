package sheetplus.checkings.domain.draw.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import sheetplus.checkings.domain.draw.dto.DrawDto.DrawEventRequestDto;
import sheetplus.checkings.domain.draw.dto.DrawDto.DrawEventResponseDto;
import sheetplus.checkings.domain.draw.dto.DrawDto.DrawUpdateRequestDto;
import sheetplus.checkings.domain.draw.dto.DrawDto.DrawUpdateResponseDto;
import sheetplus.checkings.exception.error.ErrorResponse;

@Tag(name = "Draw", description = "Draw CUD API")
public interface DrawControllerSpec {

    @Operation(summary = "Draw CREATE", description = "추첨 이벤트를 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "추첨 이벤트를 생성했습니다",
                    content = @Content(schema = @Schema(implementation = DrawEventResponseDto.class),
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
            @ApiResponse(responseCode = "404", description = "[Member or Contest] 정보를 찾을 수 없습니다",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class),
                            mediaType = "application/json"))
    })
    ResponseEntity<DrawEventResponseDto> createDraw(
            DrawEventRequestDto drawEventRequestDto
    );

    @Operation(summary = "Draw UPDATE", description = "추첨 Event Member의 수령 여부를 업데이트합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "추첨 Event Member의 수령 여부를 업데이트했습니다.",
                    content = @Content(schema = @Schema(implementation = DrawUpdateResponseDto.class),
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
            @ApiResponse(responseCode = "404", description = "[Draw] 정보를 찾을 수 없습니다",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class),
                            mediaType = "application/json"))
    })
    ResponseEntity<DrawUpdateResponseDto> updateDrawEventReceiveCondition(
            DrawUpdateRequestDto drawUpdateRequestDto
    );

    @Operation(summary = "Draw DELETE", description = "추첨 Event Member의 수령 내역을 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "추첨 Event Member의 수령 내역을 삭제했습니다",
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
            @ApiResponse(responseCode = "404", description = "[Draw] 정보를 찾을 수 없습니다",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class),
                            mediaType = "application/json"))
    })
    ResponseEntity<Void> deleteDrawEventReceiveCondition(
            @Parameter(description = "Draw PK", example = "1")
            Long id
    );

}
