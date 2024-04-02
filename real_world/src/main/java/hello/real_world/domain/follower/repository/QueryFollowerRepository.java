package hello.real_world.domain.follower.repository;

import hello.real_world.domain.follower.Follower;
import hello.real_world.domain.member.Member;

public interface QueryFollowerRepository {

    // 팔로워 정보 조회
    Follower findByMemberIdAndFollowerName(Member memberId, String followerName);

    // 팔로워 정보 저장
    Follower addFollower(Member member, String followerName);

}
