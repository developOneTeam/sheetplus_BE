package sheetplus.checking.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sheetplus.checking.domain.entity.Event;

public interface EventRepository extends JpaRepository<Event, Long> {
}
