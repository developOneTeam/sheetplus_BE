package sheetplus.checking.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sheetplus.checking.domain.entity.TemporaryMember;

public interface TemporaryMemberRepository extends JpaRepository<TemporaryMember, String> {
}
