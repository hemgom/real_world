package hello.real_world.domain.member.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import hello.real_world.domain.member.Member;
import hello.real_world.domain.member.dto.RequestLogin;
import hello.real_world.domain.member.dto.RequestUpdateMember;
import hello.real_world.domain.member.dto.ResponseMember;
import hello.real_world.domain.member.dto.ResponseProfile;
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

        RequestLogin.LoginInfo loginInfo = RequestLogin.LoginInfo.setLoginInfo(request);
        log.info("로그인 정보 : email ={}, password = {}", loginInfo.getEmail(), loginInfo.getPassword());

        Member findMember = query
                .select(member)
                .from(member)
                .where(member.email.eq(loginInfo.getEmail()),
                        member.password.eq(loginInfo.getPassword()))
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

    @Override
    public Member updateMemberInfo(RequestUpdateMember request, Authentication authentication) {

        RequestUpdateMember.UpdateInfo updateInfo = RequestUpdateMember.UpdateInfo.setUpdateInfo(request);
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

    @Override
    public ResponseProfile.ProfileInfo getProfile(String username) {
        Member findMember = query
                .select(member)
                .from(member)
                .where(member.username.eq(username))
                .fetchOne();

        if (findMember != null) {
             return ResponseProfile.ProfileInfo.builder()
                     .username(findMember.getUsername())
                     .bio(findMember.getBio())
                     .image(findMember.getImage())
                     .build();
        }

        return null;
    }

    @Override
    public ResponseProfile getFollowState(Authentication authentication,
                                      ResponseProfile.ProfileInfo profileInfo) {
        Member user = query
                .select(member)
                .from(member)
                .where(member.email.eq(authentication.getName()))
                .fetchOne();

        if (user != null) {
            if (!user.getFollowList().contains(profileInfo.getUsername())) {
                profileInfo.setFollowing("false");
            } else {
                profileInfo.setFollowing("true");
            }

            return new ResponseProfile(profileInfo);
        }

        return null;
    }

    @Override
    public Member addFollow(Authentication authentication,
                                     ResponseProfile.ProfileInfo profileInfo) {

        if (profileInfo == null) return null;

        Member user = query
                .select(member)
                .from(member)
                .where(member.email.eq(authentication.getName()))
                .fetchOne();

        if (user != null) {
            if (!user.getFollowList().contains(profileInfo.getUsername())) {
                user.getFollowList().add(profileInfo.getUsername());
            }

            return user;
        }

        return null;
    }

    @Override
    public Member delFollow(Authentication authentication,
                                     ResponseProfile.ProfileInfo profileInfo) {

        if (profileInfo == null) return null;

        Member user = query
                .select(member)
                .from(member)
                .where(member.email.eq(authentication.getName()))
                .fetchOne();

        if (user != null) {
            user.getFollowList().remove(profileInfo.getUsername());
            return user;
        }

        return null;
    }

    @Override
    public ResponseProfile checkFollowState(Member member, ResponseProfile.ProfileInfo profileInfo) {

        if (member.getFollowList().contains(profileInfo.getUsername())) {
            profileInfo.setFollowing("true");
        } else {
            profileInfo.setFollowing("false");
        }

        return new ResponseProfile(profileInfo);
    }

}
