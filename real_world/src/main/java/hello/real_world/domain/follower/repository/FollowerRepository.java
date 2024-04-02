package hello.real_world.domain.follower.repository;

import hello.real_world.domain.follower.Follower;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowerRepository extends JpaRepository<Follower, Long>, QueryFollowerRepository {
}
