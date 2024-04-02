package hello.real_world.domain.following.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import hello.real_world.domain.following.Following;
import hello.real_world.domain.member.Member;
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
    public Following findByFollowerIdAndUsername(Member follower, String username) {
        return query
                .select(following)
                .from(following)
                .where(following.follower.eq(follower), following.username.eq(username))
                .fetchOne();
    }

    @Override
    public Following addFollowing(Member follower, String username) {
        return Following.builder()
                .follower(follower)
                .username(username)
                .build();
    }

}
