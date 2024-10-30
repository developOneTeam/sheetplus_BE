package sheetplus.checkings.domain.draw.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sheetplus.checkings.domain.draw.entity.Draw;

public interface DrawRepository extends JpaRepository<Draw, Long> {
}
