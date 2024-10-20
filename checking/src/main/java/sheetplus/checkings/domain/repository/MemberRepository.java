package sheetplus.checkings.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sheetplus.checkings.domain.entity.Member;


public interface MemberRepository extends JpaRepository<Member, Long> {

}
