package hello.real_world.repository;

import hello.real_world.dto.RequestLogin;
import hello.real_world.dto.ResponseMember;

public interface QueryMemberRepository {

    // 로그인 시 정보 확인
    ResponseMember checkLoginInfo(RequestLogin request);
}
