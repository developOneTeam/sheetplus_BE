package sheetplus.checkings.domain.contest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sheetplus.checkings.domain.contest.entity.Contest;

public interface ContestRepository extends JpaRepository<Contest, Long>, ContestRepositoryCustom {

}