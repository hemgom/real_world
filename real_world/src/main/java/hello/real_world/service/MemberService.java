package hello.real_world.service;

import hello.real_world.domain.member.Member;
import hello.real_world.repository.MemberSearchCond;
import hello.real_world.repository.MemberUpdateDto;

import java.util.List;
import java.util.Optional;

// 서비스 구현체 변경 고려 인터페이스로 작성
public interface MemberService {

    Member save(Member member);

    void update(Long id, MemberUpdateDto updateParam);

    Optional<Member> findById(Long id);

    List<Member> findMember(MemberSearchCond cond);
}
