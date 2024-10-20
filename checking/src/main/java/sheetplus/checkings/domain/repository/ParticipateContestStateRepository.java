package sheetplus.checkings.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sheetplus.checkings.domain.entity.ParticipateContest;


import java.util.Optional;

public interface ParticipateContestStateRepository extends JpaRepository<ParticipateContest,Long>, ParticipateContestStateRepositoryCustom {

    Optional<ParticipateContest> findByMemberParticipateContestState_IdAndContestParticipateContestState_Id(
            Long memberId, Long contestId);

}
