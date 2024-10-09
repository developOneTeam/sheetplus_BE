package sheetplus.checking.domain.repository;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import sheetplus.checking.domain.entity.Token;

@Repository
public interface TokenRepository extends CrudRepository<Token, Long> {
}
