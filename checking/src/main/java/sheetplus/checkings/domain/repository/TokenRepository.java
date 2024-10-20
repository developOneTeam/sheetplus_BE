package sheetplus.checkings.domain.repository;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import sheetplus.checkings.domain.entity.Token;

@Repository
public interface TokenRepository extends CrudRepository<Token, Long> {
}
