package sheetplus.checking.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sheetplus.checking.domain.entity.ParticipateContest;


import java.util.Optional;

public interface ParticipateContestStateRepository extends JpaRepository<ParticipateContest,Long>, ParticipateContestStateRepositoryCustom {

    Optional<ParticipateContest> findByMemberParticipateContestState_IdAndContestParticipateContestState_Id(
            Long memberId, Long contestId);

}
