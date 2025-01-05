package sheetplus.checkings.domain.event.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import sheetplus.checkings.domain.event.dto.EventDto.EventRequestDto;
import sheetplus.checkings.domain.event.dto.EventDto.EventResponseDto;
import sheetplus.checkings.exception.error.ErrorResponse;

@Tag(name = "Event", description = "Event CUD API")
public interface EventControllerSpec {

    @Operation(summary = "Event CREATE", description = "Event를 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Event를 생성했습니다",
                    content = @Content(schema = @Schema(implementation = EventResponseDto.class),
                            mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "잘못된 HTTP 입력 요청, 요청한 입력값이 지정된 검증을 실패했습니다.",
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
            @ApiResponse(responseCode = "409", description = "시작시간이 종료시간보다 뒤에 있습니다<br> 이벤트 시간은 대회 시작/종료 기간 안에 포함되어야 합니다.",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class),
                            mediaType = "application/json"))
    })
    ResponseEntity<EventResponseDto> createEvent(
            @Parameter(description = "Contest PK", example = "1")
            Long id,
            EventRequestDto eventRequest);


    @Operation(summary = "Event UPDATE", description = "Event 데이터를 업데이트합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Event 데이터를 업데이트했습니다.",
                    content = @Content(schema = @Schema(implementation = EventResponseDto.class),
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
            @ApiResponse(responseCode = "404", description = "[Event] 정보를 찾을 수 없습니다",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class),
                            mediaType = "application/json")),
            @ApiResponse(responseCode = "409", description = "시작시간이 종료시간보다 뒤에 있습니다<br> 이벤트 시간은 대회 시작/종료 기간 안에 포함되어야 합니다.",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class),
                            mediaType = "application/json"))
    })
    ResponseEntity<EventResponseDto> updateEvent(
            @Parameter(description = "Event PK", example = "1")
            Long id,
            EventRequestDto eventRequestDto);


    @Operation(summary = "Event DELETE", description = "Event를 삭제합니다..")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Event를 삭제했습니다.",
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
            @ApiResponse(responseCode = "404", description = "[Event] 정보를 찾을 수 없습니다",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class),
                            mediaType = "application/json"))
    })
    ResponseEntity<Void> deleteEvent(
            @Parameter(description = "Event PK", example = "1")
            Long id
    );

}
