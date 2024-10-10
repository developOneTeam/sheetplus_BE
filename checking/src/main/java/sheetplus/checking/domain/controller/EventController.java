package sheetplus.checking.domain.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sheetplus.checking.domain.dto.EventRequestDto;
import sheetplus.checking.domain.dto.EventResponseDto;
import sheetplus.checking.domain.service.EventCRUDService;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("private/")
public class EventController {

    private final EventCRUDService eventCRUDService;

    @PostMapping("admin/contest/{contest}/event/create")
    public ResponseEntity<EventResponseDto> createEvent(
            @PathVariable(name = "contest") Long id,
            @RequestBody EventRequestDto eventRequest) {
        EventResponseDto eventResponseDto
                = eventCRUDService.createEvent(id, eventRequest);


        return ResponseEntity.ok()
                .body(eventResponseDto);
    }

    @PatchMapping("admin/event/{event}/update")
    public ResponseEntity<EventResponseDto> updateEvent(
            @PathVariable(name = "event") Long id,
            @RequestBody EventRequestDto eventRequestDto){
        EventResponseDto eventResponseDto
                = eventCRUDService.updateEvent(id, eventRequestDto);

        return ResponseEntity.ok()
                .body(eventResponseDto);
    }


    @DeleteMapping("admin/event/{event}/delete")
    public ResponseEntity<String> deleteEvent(
            @PathVariable(name = "event") Long id
    ){
        eventCRUDService.deleteEvent(id);

        return ResponseEntity.ok()
                .body("삭제 완료");
    }

}
