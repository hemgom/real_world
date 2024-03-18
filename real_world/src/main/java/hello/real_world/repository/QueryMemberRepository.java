package hello.real_world.repository;

import hello.real_world.dto.RequestLogin;
import hello.real_world.dto.RequestUpdateMember;
import hello.real_world.dto.ResponseMember;
import org.springframework.security.core.Authentication;

public interface QueryMemberRepository {

    // 로그인 시 정보 확인
    ResponseMember.UserInfo checkLoginInfo(RequestLogin request);

    // 사용자 정보 수정
    ResponseMember.UserInfo updateMemberInfo(RequestUpdateMember request, Authentication authentication);
}
