package sheetplus.checkings.domain.token.repository;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import sheetplus.checkings.domain.token.entity.Token;

@Repository
public interface TokenRepository extends CrudRepository<Token, Long> {
}
