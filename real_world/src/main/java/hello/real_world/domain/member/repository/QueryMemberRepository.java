package hello.real_world.domain.member.repository;

import hello.real_world.domain.member.Member;
import hello.real_world.domain.member.dto.RequestUpdateMember;
import hello.real_world.domain.member.dto.ResponseMember;
import hello.real_world.domain.member.dto.ResponseProfile;

public interface QueryMemberRepository {

    // 사용자 조회 : email, password
    Member findByEmailAndPassword(String email, String password);

    // 사용자 정보 전달 : 조회한 사용자의 정보를 응답용 DTO 에 전달
    ResponseMember.UserInfo getFindMemberInfo(Member findMember);

    // 사용자 조회 : email
    Member findByEmail(String email);

    // 사용자 정보 수정 : 조회한 사용자의 정보를 요청받은 정보로 수정
    Member updateMemberInfo(RequestUpdateMember.UpdateInfo updateInfo, Member findMember);

    // 사용자 정보 전달 : 수정한 사용자의 정보를 응답용 DTO 에 전달
    ResponseMember.UserInfo getUpdatedMemberInfo(Member updateMember);

    // 사용자 조회 : username
    Member findByUsername(String username);

    // 사용자 정보 전달 : 조회한 사용자의 정보를 응답용 DTO 에 전달
    ResponseProfile.ProfileInfo getMemberProfile(Member findMember);

}
