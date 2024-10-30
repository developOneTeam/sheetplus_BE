package sheetplus.checkings.domain.contest.repository;

import sheetplus.checkings.domain.event.dto.response.EventResponseDto;
import sheetplus.checkings.domain.enums.EventCategory;

import java.util.List;

public interface ContestRepositoryCustom {

    List<EventResponseDto> findNowAfterEvents(Long contestId);
    List<EventResponseDto> findTodayEvents(Long contestId);
    List<EventResponseDto> findParticipateEvents(Long contestId, List<EventCategory> category);

}
