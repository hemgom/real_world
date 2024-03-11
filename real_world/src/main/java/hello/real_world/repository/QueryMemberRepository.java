package hello.real_world.repository;

import hello.real_world.domain.member.Member;
import hello.real_world.dto.ResponseMember;

public interface QueryMemberRepository {
    Member findMember(ResponseMember loginInfo);
}
