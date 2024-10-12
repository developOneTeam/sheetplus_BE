package sheetplus.checking.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sheetplus.checking.domain.entity.ParticipateContestState;


import java.util.Optional;

public interface ParticipateContestStateRepository extends JpaRepository<ParticipateContestState,Long>, ParticipateContestStateRepositoryCustom {

    Optional<ParticipateContestState> findByMemberParticipateContestState_IdAndContestParticipateContestState_Id(
            Long memberId, Long contestId);

}
