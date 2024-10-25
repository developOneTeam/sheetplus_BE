package sheetplus.checkings.domain.entry.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sheetplus.checkings.domain.entry.entity.Entry;

public interface EntryRepository extends JpaRepository<Entry, Long>, EntryRepositoryCustom {

}
