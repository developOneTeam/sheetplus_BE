package sheetplus.checkings.domain.member.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sheetplus.checkings.domain.member.entity.Member;

import java.util.Optional;


public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findMemberByUniversityEmail(String email);

    @EntityGraph(value = "member.withParticipateContest", type = EntityGraph.EntityGraphType.LOAD)
    @Query("select distinct c from Member c")
    Optional<Member> findByIdAndWithGraph(Long id);

}
