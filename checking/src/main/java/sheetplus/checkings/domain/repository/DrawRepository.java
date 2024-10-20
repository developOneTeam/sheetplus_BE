package sheetplus.checkings.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sheetplus.checkings.domain.entity.Draw;

public interface DrawRepository extends JpaRepository<Draw, Long> {
}
