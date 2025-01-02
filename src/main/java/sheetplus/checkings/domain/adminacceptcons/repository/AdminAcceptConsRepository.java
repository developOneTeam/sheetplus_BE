package sheetplus.checkings.domain.adminacceptcons.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import sheetplus.checkings.domain.adminacceptcons.entity.AdminAcceptCons;

public interface AdminAcceptConsRepository extends JpaRepository<AdminAcceptCons, String> {
    Page<AdminAcceptCons> findAll(Pageable pageable);
}
