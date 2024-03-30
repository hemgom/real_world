package hello.real_world.domain.followers.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import hello.real_world.domain.followers.Followers;
import hello.real_world.domain.member.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import static hello.real_world.domain.followers.QFollowers.followers;

@Slf4j
@Repository
@RequiredArgsConstructor
public class QueryFollowersRepositoryImpl implements QueryFollowersRepository {

    private final JPAQueryFactory query;

    @Override
    public Followers findByMemberIdAndFollowerName(Member memberId, String followerName) {
        return query
                .select(followers)
                .from(followers)
                .where(followers.memberId.eq(memberId), followers.followerName.eq(followerName))
                .fetchOne();
    }

    @Override
    public Followers addFollowers(Member memberId, String followerName) {
        return Followers.builder()
                .memberId(memberId)
                .followerName(followerName)
                .build();
    }

}
