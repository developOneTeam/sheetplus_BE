package sheetplus.checking.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sheetplus.checking.domain.entity.Entry;

public interface EntryRepository extends JpaRepository<Entry, Long> {
}
