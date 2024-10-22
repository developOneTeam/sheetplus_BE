package sheetplus.checkings.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sheetplus.checkings.domain.entity.Member;

import java.util.List;


public interface MemberRepository extends JpaRepository<Member, Long> {
    List<Member> findMemberByEmail(String email);
}
