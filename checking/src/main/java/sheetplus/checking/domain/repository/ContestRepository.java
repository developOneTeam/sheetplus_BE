package sheetplus.checking.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sheetplus.checking.domain.entity.Contest;

public interface ContestRepository extends JpaRepository<Contest, Long> {

}
