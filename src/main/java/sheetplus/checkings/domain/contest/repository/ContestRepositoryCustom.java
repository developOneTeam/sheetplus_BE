package sheetplus.checkings.domain.contest.repository;

import org.springframework.data.domain.Pageable;
import sheetplus.checkings.business.page.admin.dto.AdminPageDto.ContestInfoWithCounts;
import sheetplus.checkings.domain.event.dto.EventDto.EventResponseDto;
import sheetplus.checkings.domain.enums.EventCategory;

import java.util.List;

public interface ContestRepositoryCustom {

    List<EventResponseDto> findNowAfterEvents(Long contestId);
    List<EventResponseDto> findTodayEvents(Long contestId, Pageable pageable);
    List<EventResponseDto> findParticipateEvents(Long contestId, List<EventCategory> category);
    List<ContestInfoWithCounts> findContestInfoWithCounts();

}
