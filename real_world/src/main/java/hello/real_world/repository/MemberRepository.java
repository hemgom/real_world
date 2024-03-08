package hello.real_world.repository;

import hello.real_world.domain.member.Member;
import hello.real_world.dto.UserDto;

// 메모리 구현체 변경 고려 인터페이스로 작성
public interface MemberRepository {

    // 사용자 등록
    Member save(Member member);

    // email 로 사용자 검색
    Member findMember(UserDto loginInfo);

}
