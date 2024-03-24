package hello.real_world.domain.member.repository;


import hello.real_world.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * DB 저장 : Spring Data JPA 사용
 * 그 외의 기능 : Querydsl 사용
 */
public interface MemberRepository extends JpaRepository<Member, Long>, QueryMemberRepository {
}
