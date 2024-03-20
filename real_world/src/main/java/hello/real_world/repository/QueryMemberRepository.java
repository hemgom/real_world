package hello.real_world.repository;

import hello.real_world.domain.member.Member;
import hello.real_world.dto.RequestLogin;
import hello.real_world.dto.RequestUpdateMember;
import hello.real_world.dto.ResponseMember;
import hello.real_world.dto.ResponseProfile;
import org.springframework.security.core.Authentication;

public interface QueryMemberRepository {

    // 로그인 시 정보 확인
    ResponseMember.UserInfo checkLoginInfo(RequestLogin request);

    // 사용자 정보 수정
    Member updateMemberInfo(RequestUpdateMember request, Authentication authentication);

    // 사용자 정보 조회(Id)
    ResponseMember.UserInfo findMemberById(Long id);

    // 사용자 프로필 조회
    ResponseProfile.ProfileInfo getProfile(String username);

    // 사용자 팔로우 여부 확인
    ResponseProfile checkFollow(Authentication authentication, ResponseProfile.ProfileInfo profileInfo);
}
