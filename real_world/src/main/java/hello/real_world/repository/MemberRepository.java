package hello.real_world.repository;

import hello.real_world.domain.member.Member;
import hello.real_world.dto.MemberUpdateDto;

import java.util.List;
import java.util.Optional;

// 메모리 구현체 변경 고려 인터페이스로 작성
public interface MemberRepository {

    // 사용자 등록
    Member save(Member member);

    // 특정 사용자 조회
    Optional<Member> findById(Long id);

    // 사용자 정보 수정
    void update(Long id, MemberUpdateDto updateParam);

    // 모든 사용자 조회
    List<Member> findAll(MemberSearchCond cond);
}
