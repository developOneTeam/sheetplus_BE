package sheetplus.checkings.domain.favorite.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import sheetplus.checkings.domain.favorite.entity.Favorite;

import java.util.List;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

    Page<Favorite> findAllByFavoriteMember_IdAndFavoriteContest_Id(Long memberId, Long ContestId, Pageable pageable);
    List<Favorite> findByFavoriteMember_IdAndFavoriteContest_IdAndFavoriteEvent_Id(Long memberId, Long ContestId, Long EventId);
}
