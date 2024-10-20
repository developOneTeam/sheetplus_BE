package sheetplus.checkings.domain.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import sheetplus.checkings.domain.entity.Notifications;

import java.util.List;

@Repository
public interface EventRedisRepository extends CrudRepository<Notifications, String> {
    List<Notifications> findByContestId(Long contestId);
}
