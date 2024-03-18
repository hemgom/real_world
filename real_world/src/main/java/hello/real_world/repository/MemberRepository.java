package hello.real_world.repository;


import hello.real_world.domain.member.Member;
import hello.real_world.security.JwtUtil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.authentication.AuthenticationManager;

/**
 * DB 저장 : Spring Data JPA 사용
 * 그 외의 기능 : Querydsl 사용
 */
public interface MemberRepository extends JpaRepository<Member, Long>, QueryMemberRepository {
}
