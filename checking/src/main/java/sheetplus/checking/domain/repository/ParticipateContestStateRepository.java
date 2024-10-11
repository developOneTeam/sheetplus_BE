package sheetplus.checking.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sheetplus.checking.domain.entity.ParticipateContestState;

import java.util.List;

public interface ParticipateContestStateRepository extends JpaRepository<ParticipateContestState,Long> {

    List<ParticipateContestState> findByMemberParticipateContestState_IdAndContestParticipateContestState_Id(
            Long memberId, Long contestId);

}
