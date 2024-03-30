package hello.real_world.domain.following.repository;

import hello.real_world.domain.following.Following;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowingRepository extends JpaRepository<Following, Long>, QueryFollowingRepository {
}
