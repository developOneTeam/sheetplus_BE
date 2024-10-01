package sheetplus.checking.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sheetplus.checking.domain.entity.Member;


public interface MemberRepository extends JpaRepository<Member, Long> {

}
