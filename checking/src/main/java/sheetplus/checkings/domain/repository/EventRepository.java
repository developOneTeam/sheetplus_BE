package sheetplus.checkings.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sheetplus.checkings.domain.entity.Event;

import java.time.LocalDateTime;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findByEventContestId(Long contestId);
    List<Event> findByEventContestIdAndStartTimeBetween(Long contestId, LocalDateTime start, LocalDateTime end);
}
