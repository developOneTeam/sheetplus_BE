package sheetplus.checkings.domain.participatecontest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sheetplus.checkings.domain.participatecontest.entity.ParticipateContest;


import java.util.Optional;

public interface ParticipateContestStateRepository extends JpaRepository<ParticipateContest,Long> {

    Optional<ParticipateContest> findByMemberParticipateContestState_IdAndContestParticipateContestState_Id(
            Long memberId, Long contestId);

}
