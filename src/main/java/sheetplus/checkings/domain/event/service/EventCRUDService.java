package sheetplus.checkings.domain.event.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sheetplus.checkings.business.page.admin.controller.AdminPageController;
import sheetplus.checkings.domain.event.controller.EventController;
import sheetplus.checkings.domain.event.dto.EventDto.EventRequestDto;
import sheetplus.checkings.domain.event.dto.EventDto.EventResponseDto;
import sheetplus.checkings.domain.contest.entity.Contest;
import sheetplus.checkings.domain.event.entity.Event;
import sheetplus.checkings.domain.contest.repository.ContestRepository;
import sheetplus.checkings.domain.event.repository.EventRepository;
import sheetplus.checkings.exception.exceptionMethod.ApiException;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static sheetplus.checkings.exception.error.ApiError.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventCRUDService {

    private final EventRepository eventRepository;
    private final ContestRepository contestRepository;

    @Transactional
    public EventResponseDto createEvent(Long contestId, EventRequestDto eventRequestDto) {
        Contest contest = contestRepository.findById(contestId)
                .orElseThrow(() -> new ApiException(CONTEST_NOT_FOUND));

        if(contest.getStartDate().isAfter(eventRequestDto.getEndTime()) ||
        contest.getEndDate().isBefore(eventRequestDto.getStartTime()) ||
        (contest.getStartDate().isAfter(eventRequestDto.getStartTime()) &&
                contest.getEndDate().isAfter(eventRequestDto.getEndTime())) ||
                (contest.getStartDate().isBefore(eventRequestDto.getStartTime()) &&
                        contest.getEndDate().isBefore(eventRequestDto.getEndTime()))) {
            throw new ApiException(CONTEST_EVENT_START_AFTER_END);
        }

        if(eventRequestDto.getStartTime().isAfter(eventRequestDto.getEndTime())){
            throw new ApiException(COMMON_START_AFTER_END);
        }

        Event event = Event.builder()
                .name(eventRequestDto.getName())
                .startTime(eventRequestDto.getStartTime())
                .endTime(eventRequestDto.getEndTime())
                .location(eventRequestDto.getLocation())
                .building(eventRequestDto.getBuilding())
                .speakerName(eventRequestDto.getSpeakerName())
                .major(eventRequestDto.getMajor())
                .eventCondition(eventRequestDto.getCondition())
                .eventType(eventRequestDto.getEventType())
                .eventCategory(eventRequestDto.getCategory())
                .build();
        event.setContestEvent(contest);

        Long eventId = eventRepository.save(event).getId();

        List<Link> lists = new ArrayList<>();
        lists.add(linkTo(methodOn(AdminPageController.class)
                .readAdminHome(contestId))
                .withRel("관리자 페이지 Home"));
        lists.add(linkTo(methodOn(EventController.class)
                .updateEvent(eventId, EventRequestDto.builder().build()))
                .withRel("이벤트 UPDATE"));
        lists.add(linkTo(methodOn(EventController.class)
                .deleteEvent(eventId))
                .withRel("이벤트 DELETE"));

        return EventResponseDto.builder()
                .id(eventId)
                .name(event.getName())
                .startTime(event.getStartTime())
                .endTime(event.getEndTime())
                .location(event.getLocation())
                .building(event.getBuilding())
                .speakerName(event.getSpeakerName())
                .major(event.getMajor())
                .categoryMessage(event.getEventCategory().getMessage())
                .eventTypeMessage(event.getEventType().getMessage())
                .conditionMessage(event.getEventCondition().getMessage())
                .link(lists)
                .build();
    }

    @Transactional
    public EventResponseDto updateEvent(Long eventId
            , EventRequestDto eventRequestDto) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ApiException(EVENT_NOT_FOUND));

        Contest contest = event.getEventContest();

        if(eventRequestDto.getStartTime() != null && eventRequestDto.getEndTime() != null
                && (contest.getStartDate().isAfter(eventRequestDto.getEndTime()) ||
                contest.getEndDate().isBefore(eventRequestDto.getStartTime()) ||
                (contest.getStartDate().isAfter(eventRequestDto.getStartTime()) &&
                        contest.getEndDate().isAfter(eventRequestDto.getEndTime())) ||
                (contest.getStartDate().isBefore(eventRequestDto.getStartTime()) &&
                        contest.getEndDate().isBefore(eventRequestDto.getEndTime())))) {
            throw new ApiException(CONTEST_EVENT_START_AFTER_END);
        }

        if(eventRequestDto.getStartTime() != null && eventRequestDto.getEndTime() != null
                && eventRequestDto.getStartTime().isAfter(eventRequestDto.getEndTime())){
            throw new ApiException(COMMON_START_AFTER_END);
        }


        event.updateEvent(eventRequestDto);

        List<Link> lists = new ArrayList<>();
        lists.add(linkTo(methodOn(AdminPageController.class)
                .readAdminHome(contest.getId()))
                .withRel("관리자 페이지 Home"));
        lists.add(linkTo(methodOn(EventController.class)
                .createEvent(contest.getId(), EventRequestDto.builder().build()))
                .withRel("이벤트 CREATE"));
        lists.add(linkTo(methodOn(EventController.class)
                .deleteEvent(eventId))
                .withRel("이벤트 DELETE"));

        return EventResponseDto.builder()
                .id(event.getId())
                .name(event.getName())
                .startTime(event.getStartTime())
                .endTime(event.getEndTime())
                .location(event.getLocation())
                .building(event.getBuilding())
                .speakerName(event.getSpeakerName())
                .major(event.getMajor())
                .categoryMessage(event.getEventCategory().getMessage())
                .eventTypeMessage(event.getEventType().getMessage())
                .conditionMessage(event.getEventCondition().getMessage())
                .link(lists)
                .build();
    }

    @Transactional
    public void deleteEvent(Long eventId) {
        if(eventRepository.existsById(eventId)){
            eventRepository.deleteById(eventId);
        }else{
            throw new ApiException(EVENT_NOT_FOUND);
        }

    }


}
