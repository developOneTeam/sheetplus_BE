package sheetplus.checkings.domain.event.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sheetplus.checkings.domain.event.entity.Event;

import java.time.LocalDateTime;
import java.util.Set;

public interface EventRepository extends JpaRepository<Event, Long> {

    Set<Event> findByEventContestIdAndStartTimeBetween(Long contestId, LocalDateTime start, LocalDateTime end);
}
