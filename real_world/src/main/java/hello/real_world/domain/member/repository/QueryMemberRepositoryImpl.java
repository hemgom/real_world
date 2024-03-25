package hello.real_world.domain.member.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import hello.real_world.domain.member.Member;
import hello.real_world.domain.member.dto.RequestUpdateMember;
import hello.real_world.domain.member.dto.ResponseMember;
import hello.real_world.domain.member.dto.ResponseProfile;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static hello.real_world.domain.member.QMember.member;

@Slf4j
@Repository
@RequiredArgsConstructor
public class QueryMemberRepositoryImpl implements QueryMemberRepository {

    private final JPAQueryFactory query;

    /**
     * 조회 기능 메서드 : email, password 를 통해 DB 에서 일치하는 member 를 조회
     */
    @Override
    public Member findByEmailAndPassword(String email, String password) {
        return Optional.ofNullable(query
                        .select(member)
                        .from(member)
                        .where(member.email.eq(email), member.password.eq(password))
                        .fetchOne())
                .orElseThrow(() -> new NoSuchElementException("입력한 정보와 일치하는 사용자가 없습니다."));
    }

    /**
     * 전달 기능 메서드 : 조회한 사용자의 정보를 DTO 타입으로 가져옴
     */
    @Override
    public ResponseMember.UserInfo getFindMemberInfo(Member findMember) {
        return ResponseMember.UserInfo.builder()
                .email(findMember.getEmail())
                .username(findMember.getUsername())
                .bio(findMember.getBio())
                .image(findMember.getImage())
                .build();
    }

    /**
     * 조회 기능 메서드 : email 을 통해 DB 에서 일치하는 member 를 조회
     */
    @Override
    public Member findByEmail(String email) {
        return Optional.ofNullable(query
                        .select(member)
                        .from(member)
                        .where(member.email.eq(email))
                        .fetchOne())
                .orElseThrow(() -> new NoSuchElementException("인증 정보와 일치하는 사용자가 없습니다."));
    }

    /**
     * 수정 기능 메서드 : 요청받은 정보로 기존 사용자의 정보를 수정
     */
    @Override
    public Member updateMemberInfo(RequestUpdateMember.UpdateInfo updateInfo, Member findMember) {
        findMember.updateMemberInfo(updateInfo);
        return findMember;
    }

    /**
     * 전달 기능 메서드 : 수정한 사용자 정보를 DTO 타입으로 가져옴
     */
    @Override
    public ResponseMember.UserInfo getUpdatedMemberInfo(Member updateMember) {
        return ResponseMember.UserInfo.builder()
                .email(updateMember.getEmail())
                .username(updateMember.getUsername())
                .bio(updateMember.getBio())
                .image(updateMember.getImage())
                .build();
    }

    /**
     * 조회 기능 메서드 : username 을 통해 일치하는 member 를 조회
     */
    @Override
    public Member findByUsername(String username) {
        return Optional.ofNullable(query
                        .select(member)
                        .from(member)
                        .where(member.username.eq(username))
                        .fetchOne())
                .orElseThrow(() -> new NoSuchElementException("요청한 사용자를 찾을 수 없습니다."));
    }

    /**
     * 전달 기능 메서드 : 조회한 사용자의 정보를 DTO 타입으로 가져옴
     */
    @Override
    public ResponseProfile.ProfileInfo getMemberProfile(Member findMember) {
        return ResponseProfile.ProfileInfo.builder()
                .username(findMember.getUsername())
                .bio(findMember.getBio())
                .image(findMember.getImage())
                .build();
    }

    /**
     * 조회 기능 메서드 : 조회한 사용자 팔로우 유무에 따라 특정 값으로 수정
     */
    @Override
    public ResponseProfile.ProfileInfo setFollowState(ResponseProfile.ProfileInfo profile, List<String> followList) {
        if (followList.contains(profile.getUsername())) {
            profile.setFollowing("true");
        } else {
            profile.setFollowing("false");
        }

        return profile;
    }

    /**
     * 수정 기능 메서드 : 조회한 사용자의 username 을 로그인 사용자의 followList 에 추가
     */
    @Override
    public Member addFollow(Member loginMember, String username) {
        if (!loginMember.getFollowList().contains(username)) {
            loginMember.getFollowList().add(username);
        }
        return loginMember;
    }

    /**
     * 수정 기능 메서드 : 조회한 사용자의 username 을 로그인 사용자의 followList 에서 제거
     */
    @Override
    public Member delFollow(Member loginMember, String username) {
        loginMember.getFollowList().remove(username);
        return loginMember;
    }

}
