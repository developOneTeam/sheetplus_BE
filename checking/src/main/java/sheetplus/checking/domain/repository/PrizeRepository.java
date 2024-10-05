package sheetplus.checking.domain.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import sheetplus.checking.domain.entity.Prize;

public interface PrizeRepository extends JpaRepository<Prize, Long> {

}
