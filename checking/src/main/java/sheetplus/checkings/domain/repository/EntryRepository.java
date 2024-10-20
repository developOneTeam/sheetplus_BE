package sheetplus.checkings.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sheetplus.checkings.domain.entity.Entry;

public interface EntryRepository extends JpaRepository<Entry, Long>, EntryRepositoryCustom {

}
