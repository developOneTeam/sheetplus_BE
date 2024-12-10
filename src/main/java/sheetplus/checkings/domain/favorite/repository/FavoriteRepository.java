package sheetplus.checkings.domain.favorite.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sheetplus.checkings.domain.favorite.entity.Favorite;

import java.util.ArrayList;
import java.util.List;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

    ArrayList<Favorite> findByFavoriteMember_IdAndFavoriteContest_Id(Long memberId, Long ContestId);
    List<Favorite> findByFavoriteMember_IdAndFavoriteContest_IdAndFavoriteEvent_Id(Long memberId, Long ContestId, Long EventId);
}
