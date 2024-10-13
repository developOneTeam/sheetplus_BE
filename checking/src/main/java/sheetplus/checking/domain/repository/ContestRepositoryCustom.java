package sheetplus.checking.domain.repository;

import sheetplus.checking.domain.dto.EventResponseDto;
import sheetplus.checking.domain.entity.enums.EventCategory;

import java.util.List;

public interface ContestRepositoryCustom {

    List<EventResponseDto> findNowAfterEvents(Long contestId);
    List<EventResponseDto> findTodayEvents(Long contestId);
    List<EventResponseDto> findParticipateEvents(Long contestId, List<EventCategory> category);

}
