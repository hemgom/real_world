package hello.real_world.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import hello.real_world.domain.member.Member;
import hello.real_world.dto.FindEntityDto;
import hello.real_world.dto.ResponseMember;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import static hello.real_world.domain.member.QMember.member;
@Slf4j
@Repository
@RequiredArgsConstructor
public class QueryMemberRepositoryImpl implements QueryMemberRepository {

    private final JPAQueryFactory query;

    @Override
    public Member findMember(ResponseMember loginInfo) {

//        String email = loginInfo.getEmail();
//        String password = loginInfo.getPassword();
//        log.info("input data : email = {}, password = {}", email, password);
//
//        return query
//                .select(member)
//                .from(member)
//                .where(FindEntityDto.likeEmail(email), FindEntityDto.likePassword(password))
//                .fetchOne();

        return null;
    }

}
