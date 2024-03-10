package hello.real_world.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import hello.real_world.domain.member.Member;
import hello.real_world.dto.FindEntityDto;
import hello.real_world.dto.UserDto;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import static hello.real_world.domain.member.QMember.member;

@Slf4j
@Repository
@Transactional
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepository {

    private final SpringDataJpaMemberRepository repository;
    private final JPAQueryFactory query;

    @Override
    public Member save(Member member) {
        repository.save(member);
        return member;
    }

    @Override
    public Member findMember(UserDto loginInfo) {

        String email = loginInfo.getUser().getEmail();
        String password = loginInfo.getUser().getPassword();
        log.info("input data : email = {}, password = {}", email, password);

        return query
                .select(member)
                .from(member)
                .where(FindEntityDto.likeEmail(email), FindEntityDto.likePassword(password))
                .fetchOne();
    }

}
