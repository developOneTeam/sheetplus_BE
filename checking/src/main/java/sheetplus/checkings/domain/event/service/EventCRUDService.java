package sheetplus.checkings.domain.event.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sheetplus.checkings.domain.event.dto.request.EventRequestDto;
import sheetplus.checkings.domain.event.dto.response.EventResponseDto;
import sheetplus.checkings.domain.contest.entity.Contest;
import sheetplus.checkings.domain.event.entity.Event;
import sheetplus.checkings.domain.contest.repository.ContestRepository;
import sheetplus.checkings.domain.event.repository.EventRepository;
import sheetplus.checkings.exception.exceptionMethod.ApiException;
import sheetplus.checkings.util.CryptoUtil;

import static sheetplus.checkings.exception.error.ApiError.CONTEST_NOT_FOUND;
import static sheetplus.checkings.exception.error.ApiError.EVENT_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventCRUDService {

    private final EventRepository eventRepository;
    private final ContestRepository contestRepository;
    private final CryptoUtil cryptoUtil;

    @Transactional
    public EventResponseDto createEvent(Long contestId, EventRequestDto eventRequestDto) {
        Contest contest = contestRepository.findById(contestId)
                .orElseThrow(() -> new ApiException(CONTEST_NOT_FOUND));

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

        Long id = eventRepository.save(event).getId();

        return EventResponseDto.builder()
                .secureId(cryptoUtil.encrypt(id))
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
                .build();
    }

    @Transactional
    public EventResponseDto updateEvent(Long eventId
            , EventRequestDto eventRequestDto) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ApiException(EVENT_NOT_FOUND));;

        event.updateEvent(eventRequestDto);

        return EventResponseDto.builder()
                .secureId(cryptoUtil.encrypt(event.getId()))
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
