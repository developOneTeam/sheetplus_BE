package sheetplus.checkings.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sheetplus.checkings.domain.entity.Contest;

public interface ContestRepository extends JpaRepository<Contest, Long>, ContestRepositoryCustom {

}
