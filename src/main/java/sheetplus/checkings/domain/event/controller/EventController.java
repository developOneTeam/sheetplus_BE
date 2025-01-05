package sheetplus.checkings.domain.event.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import sheetplus.checkings.domain.event.dto.EventDto.EventRequestDto;
import sheetplus.checkings.domain.event.dto.EventDto.EventResponseDto;
import sheetplus.checkings.domain.event.service.EventCRUDService;
import sheetplus.checkings.business.notifications.service.EventSchedulerService;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("private/admin")
public class EventController implements EventControllerSpec{

    private final EventCRUDService eventCRUDService;
    private final EventSchedulerService eventSchedulerService;

    @PostMapping("/contests/{contest}/event/v1")
    public ResponseEntity<EventResponseDto> createEvent(
            @PathVariable(name = "contest") Long id,
            @RequestBody @Validated EventRequestDto eventRequest) {
        EventResponseDto eventResponseDto
                = eventCRUDService.createEvent(id, eventRequest);
        eventSchedulerService.scheduleNewEvent(eventResponseDto);

        return ResponseEntity.created(URI.create(""))
                .body(eventResponseDto);
    }

    @PutMapping("/events/{event}/v1")
    public ResponseEntity<EventResponseDto> updateEvent(
            @PathVariable(name = "event") Long id,
            @RequestBody @Validated EventRequestDto eventRequestDto){
        EventResponseDto eventResponseDto
                = eventCRUDService.updateEvent(id, eventRequestDto);
        eventSchedulerService.scheduleUpdateEvent(eventResponseDto);

        return ResponseEntity.ok(eventResponseDto);
    }


    @DeleteMapping("/events/{event}/v1")
    public ResponseEntity<Void> deleteEvent(
            @PathVariable(name = "event") Long id
    ){
        eventSchedulerService.scheduleDeleteEvent(id);
        eventCRUDService.deleteEvent(id);

        return ResponseEntity.noContent().build();
    }

}
