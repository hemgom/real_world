package hello.real_world.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import hello.real_world.domain.member.Member;
import hello.real_world.dto.RequestLogin;
import hello.real_world.dto.RequestUpdateMember;
import hello.real_world.dto.ResponseMember;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Repository;

import static hello.real_world.domain.member.QMember.member;

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
    public ResponseMember.UserInfo checkLoginInfo(RequestLogin request) {

        RequestLogin.LoginInfo loginInfo = RequestLogin.LoginInfo.addLoginInfo(request);
        log.info("로그인 정보 : email ={}, password = {}", loginInfo.getEmail(), loginInfo.getPassword());

        Member findMember = query
                .select(member)
                .from(member)
                .where(member.email.eq(loginInfo.getEmail()),
                        member.password.eq(loginInfo.getPassword()))
                .fetchOne();

        if (findMember != null) {

            ResponseMember.UserInfo userInfo = ResponseMember.UserInfo.builder()
                    .email(findMember.getEmail())
                    .username(findMember.getUsername())
                    .bio(findMember.getBio())
                    .image(findMember.getImage())
                    .build();

            return userInfo;
        }

        return null;
    }

    @Override
    public Member updateMemberInfo(RequestUpdateMember request, Authentication authentication) {

        RequestUpdateMember.UpdateInfo updateInfo = RequestUpdateMember.UpdateInfo.addUpdateInfo(request);
        log.info("사용자 수정 정보 : email = {}", updateInfo.getEmail());
        log.info("사용자 수정 정보 : username = {}", updateInfo.getUsername());
        log.info("사용자 수정 정보 : password = {}", updateInfo.getPassword());
        log.info("사용자 수정 정보 : bio = {}", updateInfo.getBio());
        log.info("사용자 수정 정보 : image = {}", updateInfo.getImage());

        Member findMember = query
                .select(member)
                .from(member)
                .where(member.email.eq(authentication.getName()))
                .fetchOne();

        if (findMember != null) {
            findMember.updateMemberInfo(updateInfo);
        }

        return findMember;
    }

    @Override
    public ResponseMember.UserInfo findMemberById(Long id) {
        Member findMember = query
                .select(member)
                .from(member)
                .where(member.id.eq(id))
                .fetchOne();

        if (findMember != null) {
            return ResponseMember.UserInfo.builder()
                    .email(findMember.getEmail())
                    .username(findMember.getUsername())
                    .bio(findMember.getBio())
                    .image(findMember.getImage())
                    .build();
        }

        return null;
    }

}
