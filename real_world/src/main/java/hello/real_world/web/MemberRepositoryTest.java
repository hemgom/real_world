package hello.real_world.web;

import hello.real_world.domain.member.MemberTest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepositoryTest extends JpaRepository<MemberTest, Long> {
    Optional<MemberTest> findByUsername(String username);
}
