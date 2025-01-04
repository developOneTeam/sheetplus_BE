package sheetplus.checkings.domain.event.controller;


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
import sheetplus.checkings.domain.event.dto.EventDto.EventRequestDto;
import sheetplus.checkings.domain.event.dto.EventDto.EventResponseDto;
import sheetplus.checkings.domain.event.service.EventCRUDService;
import sheetplus.checkings.business.notifications.service.EventSchedulerService;
import sheetplus.checkings.exception.error.ErrorResponse;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("private/admin")
@Tag(name = "Event", description = "Event CUD API")
public class EventController {

    private final EventCRUDService eventCRUDService;
    private final EventSchedulerService eventSchedulerService;

    @PostMapping("/contests/{contest}/event/v1")
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
            @ApiResponse(responseCode = "409", description = "시작시간이 종료시간보다 뒤에 있습니다, 이벤트 시간은 대회 시작/종료 기간 안에 포함되어야 합니다.",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class),
                            mediaType = "application/json"))
    })
    public ResponseEntity<EventResponseDto> createEvent(
            @Parameter(description = "Contest PK", example = "1")
            @PathVariable(name = "contest") Long id,
            @RequestBody @Validated EventRequestDto eventRequest) {
        EventResponseDto eventResponseDto
                = eventCRUDService.createEvent(id, eventRequest);
        eventSchedulerService.scheduleNewEvent(eventResponseDto);

        return ResponseEntity.created(URI.create(""))
                .body(eventResponseDto);
    }

    @PutMapping("/events/{event}/v1")
    @Operation(summary = "Event UPDATE", description = "Event 데이터를 업데이트합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Event 데이터를 업데이트했습니다.",
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
            @ApiResponse(responseCode = "404", description = "[Event] 정보를 찾을 수 없습니다",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class),
                            mediaType = "application/json")),
            @ApiResponse(responseCode = "409", description = "시작시간이 종료시간보다 뒤에 있습니다, 이벤트 시간은 대회 시작/종료 기간 안에 포함되어야 합니다.",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class),
                            mediaType = "application/json"))
    })
    public ResponseEntity<EventResponseDto> updateEvent(
            @Parameter(description = "Event PK", example = "1")
            @PathVariable(name = "event") Long id,
            @RequestBody @Validated EventRequestDto eventRequestDto){
        EventResponseDto eventResponseDto
                = eventCRUDService.updateEvent(id, eventRequestDto);
        eventSchedulerService.scheduleUpdateEvent(eventResponseDto);

        return ResponseEntity.ok(eventResponseDto);
    }


    @DeleteMapping("/events/{event}/v1")
    @Operation(summary = "Event DELETE", description = "Event를 삭제합니다..")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Event를 삭제했습니다."),
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
    public ResponseEntity<String> deleteEvent(
            @Parameter(description = "Event PK", example = "1")
            @PathVariable(name = "event") Long id
    ){
        eventSchedulerService.scheduleDeleteEvent(id);
        eventCRUDService.deleteEvent(id);

        return ResponseEntity.noContent().build();
    }

}
