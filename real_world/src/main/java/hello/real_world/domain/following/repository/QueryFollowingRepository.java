package hello.real_world.domain.following.repository;

import hello.real_world.domain.following.Following;
import hello.real_world.domain.member.Member;

public interface QueryFollowingRepository {

    // 팔로잉 정보 조회
    Following findByFollowerIdAndUsername(Member followerId, String username);

    // 팔로잉 정보 추가
    Following addFollowing(Member follower, String username);

}
