package sheetplus.checkings.domain.draw.controller;

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
import sheetplus.checkings.domain.draw.dto.DrawDto.DrawEventRequestDto;
import sheetplus.checkings.domain.draw.dto.DrawDto.DrawEventResponseDto;
import sheetplus.checkings.domain.draw.dto.DrawDto.DrawUpdateRequestDto;
import sheetplus.checkings.domain.draw.dto.DrawDto.DrawUpdateResponseDto;
import sheetplus.checkings.domain.draw.service.DrawEventService;
import sheetplus.checkings.deprecated.prize.PrizeConditionRequestDto;
import sheetplus.checkings.exception.error.ErrorResponse;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("private/admin")
@Slf4j
@Tag(name = "Draw", description = "Draw CUD API")
public class DrawController {

    private final DrawEventService drawEventService;


    /**
     *
     * Deprecated
     * 사유: 증정 기능 비즈니스 정책상 사용 보류
     *
     */
    //@PostMapping("private/admin/prize/target/refresh")
    public void eventTargetRefresh(
            @RequestBody PrizeConditionRequestDto prizeConditionRequestDto) {
        drawEventService.participateStateRefresh(prizeConditionRequestDto.getCondition());
        // 조회 기능 추가 필요

    }

    @PostMapping("/draw/v1")
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
    public ResponseEntity<DrawEventResponseDto> createDraw(
            @RequestBody @Validated DrawEventRequestDto drawEventRequestDto
    ){
        return ResponseEntity.created(URI.create(""))
                .body(drawEventService.createDrawMember(drawEventRequestDto));
    }

    @PutMapping("/draw/v1")
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
    public ResponseEntity<DrawUpdateResponseDto> updateDrawEventReceiveCondition(
            @RequestBody @Validated DrawUpdateRequestDto drawUpdateRequestDto
            ){
        
        return ResponseEntity.ok(drawEventService.updateDrawReceived(drawUpdateRequestDto));
    }
    
    @DeleteMapping("/draw/{draw}/v1")
    @Operation(summary = "Draw DELETE", description = "추첨 Event Member의 수령 내역을 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "추첨 Event Member의 수령 내역을 삭제했습니다"),
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
    public ResponseEntity<Void> deleteDrawEventReceiveCondition(
            @Parameter(description = "Draw PK", example = "1")
            @PathVariable(name = "draw") Long id
    ){
        drawEventService.deleteDraw(id);
        return ResponseEntity.noContent().build();
    }




}
