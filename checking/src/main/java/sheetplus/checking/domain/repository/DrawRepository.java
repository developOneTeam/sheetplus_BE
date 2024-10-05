package sheetplus.checking.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sheetplus.checking.domain.entity.Draw;

public interface DrawRepository extends JpaRepository<Draw, Long> {
}
