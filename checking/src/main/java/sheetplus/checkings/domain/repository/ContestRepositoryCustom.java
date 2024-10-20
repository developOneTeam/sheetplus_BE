package sheetplus.checkings.domain.repository;

import sheetplus.checkings.domain.dto.EventResponseDto;
import sheetplus.checkings.domain.entity.enums.EventCategory;

import java.util.List;

public interface ContestRepositoryCustom {

    List<EventResponseDto> findNowAfterEvents(Long contestId);
    List<EventResponseDto> findTodayEvents(Long contestId);
    List<EventResponseDto> findParticipateEvents(Long contestId, List<EventCategory> category);

}
