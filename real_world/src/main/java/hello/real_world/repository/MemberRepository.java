package hello.real_world.repository;

import hello.real_world.domain.member.Member;

import java.util.List;
import java.util.Optional;

// 메모리 구현체 변경 고려 인터페이스로 작성
public interface MemberRepository {

    Member save(Member member);

    void update(Long id, MemberUpdateDto updateParam);

    Optional<Member> findById(Long id);

    List<Member> findAll(MemberSearchCond cond);
}
