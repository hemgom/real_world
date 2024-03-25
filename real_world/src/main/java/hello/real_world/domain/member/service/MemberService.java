package hello.real_world.domain.member.service;

import hello.real_world.domain.member.dto.*;

// 서비스 구현체 변경 고려 인터페이스로 작성
public interface MemberService {

    // 사용자 등록
    ResponseMember save(RequestAddMember request);

    // 사용자 로그인
    ResponseMember userLogin(RequestLogin request);

    // 사용자 정보 수정
    ResponseMember updateMember(RequestUpdateMember request, String jwt, String userEmail);

    // 사용자 프로필 조회
    ResponseProfile getProfile(String username, String userEmail);

    // 사용자 팔로우 추가
    ResponseProfile addFollow(String username, String userEmail);

    // 사용자 팔로우 제거
    ResponseProfile delFollow(String username, String userEmail);

}
