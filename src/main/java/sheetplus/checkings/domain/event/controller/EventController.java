package sheetplus.checkings.domain.event.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sheetplus.checkings.domain.event.dto.request.EventRequestDto;
import sheetplus.checkings.domain.event.dto.response.EventResponseDto;
import sheetplus.checkings.domain.event.service.EventCRUDService;
import sheetplus.checkings.business.notifications.service.EventSchedulerService;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("private/admin")
public class EventController {

    private final EventCRUDService eventCRUDService;
    private final EventSchedulerService eventSchedulerService;

    @PostMapping("/contests/{contest}/event")
    public ResponseEntity<EventResponseDto> createEvent(
            @PathVariable(name = "contest") Long id,
            @RequestBody EventRequestDto eventRequest) {
        EventResponseDto eventResponseDto
                = eventCRUDService.createEvent(id, eventRequest);
        eventSchedulerService.scheduleNewEvent(eventResponseDto);

        return ResponseEntity.created(URI.create(""))
                .body(eventResponseDto);
    }

    @PatchMapping("/events/{event}")
    public ResponseEntity<EventResponseDto> updateEvent(
            @PathVariable(name = "event") Long id,
            @RequestBody EventRequestDto eventRequestDto){
        EventResponseDto eventResponseDto
                = eventCRUDService.updateEvent(id, eventRequestDto);
        eventSchedulerService.scheduleUpdateEvent(eventResponseDto);

        return ResponseEntity.ok(eventResponseDto);
    }


    @DeleteMapping("/events/{event}")
    public ResponseEntity<String> deleteEvent(
            @PathVariable(name = "event") Long id
    ){
        eventSchedulerService.scheduleDeleteEvent(id);
        eventCRUDService.deleteEvent(id);

        return ResponseEntity.noContent().build();
    }

}
