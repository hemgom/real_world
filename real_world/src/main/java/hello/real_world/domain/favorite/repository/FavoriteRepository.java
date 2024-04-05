package hello.real_world.domain.favorite.repository;

import hello.real_world.domain.favorite.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoriteRepository extends JpaRepository<Favorite, Long>, QueryFavoriteRepository {
}
