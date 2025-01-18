package sheetplus.checkings.domain.contest.repository;

import org.springframework.data.domain.Pageable;
import sheetplus.checkings.business.page.admin.dto.AdminPageDto.AdminContestStatsDto;
import sheetplus.checkings.business.page.admin.dto.AdminPageDto.AdminEventStatsDto;
import sheetplus.checkings.business.page.admin.dto.AdminPageDto.ContestInfoWithCounts;
import sheetplus.checkings.domain.contest.dto.ContestDto.ContestInfoResponseDto;
import sheetplus.checkings.domain.entry.dto.EntryDto.EntryExceptLinksResponseDto;
import sheetplus.checkings.domain.event.dto.EventDto.EventExceptLinksResponseDto;

import java.util.List;

public interface ContestRepositoryCustom {

    List<EventExceptLinksResponseDto> findNowAfterEvents(Long contestId, String building);
    List<EventExceptLinksResponseDto> findTodayEvents(Long contestId, Pageable pageable);
    List<EventExceptLinksResponseDto> findParticipateEvents(Long ParticipateId);
    List<ContestInfoWithCounts> findContestInfoWithCounts();
    List<EntryExceptLinksResponseDto> findContestWithEntries(Long contestId, Pageable pageable);
    AdminEventStatsDto findContestWithEvents(Long contestId, Pageable pageable);
    AdminContestStatsDto findContestStats(Long contestId);
    List<ContestInfoResponseDto> findAllContestInfo(Pageable pageable);
}
