package sheetplus.checkings.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sheetplus.checkings.domain.entity.TemporaryMember;

public interface TemporaryMemberRepository extends JpaRepository<TemporaryMember, String> {
}
