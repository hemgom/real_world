package hello.real_world.service;

import hello.real_world.dto.RequestAddMember;
import hello.real_world.dto.RequestLogin;
import hello.real_world.dto.ResponseMember;

// 서비스 구현체 변경 고려 인터페이스로 작성
public interface MemberService {

    // 사용자 등록
    ResponseMember save(RequestAddMember request);

    // 사용자 로그인
    ResponseMember userLogin(RequestLogin request);

}
