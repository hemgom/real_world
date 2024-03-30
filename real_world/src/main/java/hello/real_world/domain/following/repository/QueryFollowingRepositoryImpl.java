package hello.real_world.domain.following.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import hello.real_world.domain.following.Following;
import hello.real_world.domain.member.Member;
import hello.real_world.domain.member.dto.ResponseProfile;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import static hello.real_world.domain.following.QFollowing.following;

@Slf4j
@Repository
@RequiredArgsConstructor
public class QueryFollowingRepositoryImpl implements QueryFollowingRepository {

    private final JPAQueryFactory query;

    @Override
    public Following findByFollowerIdAndUsername(Member followerId, String username) {
        return query
                .select(following)
                .from(following)
                .where(following.followerId.eq(followerId), following.username.eq(username))
                .fetchOne();
    }

    @Override
    public Following addFollowing(Member followerId, String username) {
        return Following.builder()
                .followerId(followerId)
                .username(username)
                .build();
    }

    @Override
    public ResponseProfile.ProfileInfo setFollowState(ResponseProfile.ProfileInfo profile, Following following) {
        if (following != null) {
            profile.setFollowing("true");
        } else {
            profile.setFollowing("false");
        }

        return profile;
    }

}
