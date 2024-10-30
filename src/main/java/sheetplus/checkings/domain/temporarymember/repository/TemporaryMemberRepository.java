package sheetplus.checkings.domain.temporarymember.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sheetplus.checkings.domain.temporarymember.entity.TemporaryMember;

public interface TemporaryMemberRepository extends JpaRepository<TemporaryMember, String> {
}
