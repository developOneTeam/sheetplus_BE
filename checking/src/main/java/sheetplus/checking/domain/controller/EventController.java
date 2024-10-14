package sheetplus.checking.domain.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import sheetplus.checking.domain.dto.EventRequestDto;
import sheetplus.checking.domain.dto.EventResponseDto;
import sheetplus.checking.domain.service.EventCRUDService;
import sheetplus.checking.response.Api;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("private/")
public class EventController {

    private final EventCRUDService eventCRUDService;

    @PostMapping("admin/contest/{contest}/event/create")
    public Api<EventResponseDto> createEvent(
            @PathVariable(name = "contest") Long id,
            @RequestBody EventRequestDto eventRequest) {
        EventResponseDto eventResponseDto
                = eventCRUDService.createEvent(id, eventRequest);


        return Api.CREATED(eventResponseDto);
    }

    @PatchMapping("admin/event/{event}/update")
    public Api<EventResponseDto> updateEvent(
            @PathVariable(name = "event") Long id,
            @RequestBody EventRequestDto eventRequestDto){
        EventResponseDto eventResponseDto
                = eventCRUDService.updateEvent(id, eventRequestDto);

        return Api.UPDATED(eventResponseDto);
    }


    @DeleteMapping("admin/event/{event}/delete")
    public Api<String> deleteEvent(
            @PathVariable(name = "event") Long id
    ){
        eventCRUDService.deleteEvent(id);

        return Api.DELETE("삭제 완료");
    }

}
