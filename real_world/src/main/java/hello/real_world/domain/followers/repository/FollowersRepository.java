package hello.real_world.domain.followers.repository;

import hello.real_world.domain.followers.Followers;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowersRepository extends JpaRepository<Followers, Long>, QueryFollowersRepository {
}
