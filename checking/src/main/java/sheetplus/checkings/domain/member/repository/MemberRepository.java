package sheetplus.checkings.domain.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sheetplus.checkings.domain.member.entity.Member;

import java.util.Optional;


public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findMemberByUniversityEmail(String email);
}
