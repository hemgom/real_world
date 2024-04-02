package hello.real_world.domain.follower.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import hello.real_world.domain.follower.Follower;
import hello.real_world.domain.member.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import static hello.real_world.domain.follower.QFollower.follower;

@Slf4j
@Repository
@RequiredArgsConstructor
public class QueryFollowerRepositoryImpl implements QueryFollowerRepository {

    private final JPAQueryFactory query;

    @Override
    public Follower findByMemberIdAndFollowerName(Member member, String followerName) {
        return query
                .select(follower)
                .from(follower)
                .where(follower.member.eq(member), follower.followerName.eq(followerName))
                .fetchOne();
    }

    @Override
    public Follower addFollower(Member member, String followerName) {
        return Follower.builder()
                .member(member)
                .followerName(followerName)
                .build();
    }

}
