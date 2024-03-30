package hello.real_world.domain.followers.repository;

import hello.real_world.domain.followers.Followers;
import hello.real_world.domain.member.Member;

public interface QueryFollowersRepository {

    // 팔로워 정보 조회
    Followers findByMemberIdAndFollowerName(Member memberId, String followerName);

    // 팔로워 정보 저장
    Followers addFollowers(Member memberId, String followerName);

}
