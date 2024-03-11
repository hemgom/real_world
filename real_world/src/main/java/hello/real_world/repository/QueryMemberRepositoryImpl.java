package hello.real_world.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import hello.real_world.domain.member.Member;
import hello.real_world.domain.member.QMember;
import hello.real_world.dto.RequestLogin;
import hello.real_world.dto.ResponseMember;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

/**
 * 엔티티에서 요청 정보와 같은 사용자를 검색
 * email 과 password 가 둘다 일치하면 해당 사용자의 정보를 반환
 */
@Slf4j
@Repository
@RequiredArgsConstructor
public class QueryMemberRepositoryImpl implements QueryMemberRepository {

    private final JPAQueryFactory query;

    @Override
    public ResponseMember checkLoginInfo(RequestLogin request) {

        RequestLogin.LoginInfo loginInfo = RequestLogin.LoginInfo.addLoginInfo(request);
        log.info("로그인 정보 : email ={}, password = {}", loginInfo.getEmail(), loginInfo.getPassword());

        Member findMember = query
                .select(QMember.member)
                .from(QMember.member)
                .where(QMember.member.email.like(loginInfo.getEmail()),
                        QMember.member.password.like(loginInfo.getPassword()))
                .fetchOne();

        if (findMember != null) {

            ResponseMember.UserInfo userInfo = ResponseMember.UserInfo.builder()
                    .email(findMember.getEmail())
                    .username(findMember.getUsername())
                    .bio(findMember.getBio())
                    .image(findMember.getImage())
                    .build();

            return new ResponseMember(userInfo);
        }

        return null;
    }

}
