package sheetplus.checking.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sheetplus.checking.domain.entity.ParticipateState;

public interface ParticipateStateRepository extends JpaRepository<ParticipateState, Long> {
}
