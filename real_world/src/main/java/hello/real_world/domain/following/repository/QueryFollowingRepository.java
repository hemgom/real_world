package hello.real_world.domain.following.repository;

import hello.real_world.domain.following.Following;
import hello.real_world.domain.member.Member;
import hello.real_world.domain.member.dto.ResponseProfile;

public interface QueryFollowingRepository {

    // 팔로잉 정보 조회
    Following findByFollowerIdAndUsername(Member followerId, String username);

    // 팔로잉 정보 추가
    Following addFollowing(Member followerId, String username);

    // 팔로우 상태 세팅
    ResponseProfile.ProfileInfo setFollowState(ResponseProfile.ProfileInfo profile, Following following);

}
