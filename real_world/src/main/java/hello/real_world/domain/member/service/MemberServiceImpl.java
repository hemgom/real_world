package hello.real_world.domain.member.service;

import hello.real_world.domain.member.Member;
import hello.real_world.domain.member.dto.*;
import hello.real_world.domain.member.repository.MemberRepository;
import hello.real_world.security.JwtInfo;
import hello.real_world.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

// 'MemberService' 구현체 클래스, 대부분 기능을 'repository' 에 위임
@Slf4j
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;    // 사용자 등록, 수정, 조회
    private final JwtUtil jwtUtil;                      // JWT 발급, 재발급, 인증

    @Override
    public ResponseMember save(RequestAddMember request) {
        RequestAddMember.JoinInfo joinInfo = RequestAddMember.JoinInfo.setJoinInfo(request);
        memberRepository.save(Member.setMemberInfo(joinInfo));
        log.info("----------------------------------------------------------------");
        log.info("사용자 등록 완료");

        return new ResponseMember(ResponseMember.UserInfo.setUserInfo(joinInfo));
    }

    @Override
    public ResponseMember userLogin(RequestLogin request) {
        String email = request.getUser().getEmail();
        String password = request.getUser().getPassword();
        log.info("----------------------------------------------------------------");
        log.info("LoginEmail = {}", email);
        log.info("LoginPassword = {}", password);

        Member findMember = memberRepository.findByEmailAndPassword(email, password);
        ResponseMember.UserInfo userInfo = memberRepository.getFindMemberInfo(findMember);

        JwtInfo jwtInfo = jwtUtil.createToken(email);
        log.info("CreateAccessToken = {}", jwtInfo.getAccessToken());

        userInfo.setToken(jwtInfo.getAccessToken());
        log.info("UserAccessToken = {}", userInfo.getToken());

        return new ResponseMember(userInfo);
    }

    @Override
    public ResponseMember updateMember(RequestUpdateMember request, String jwt, String userEmail) {
        Member findMember = memberRepository.findByEmail(userEmail);
        Member updateMember = memberRepository.updateMemberInfo(request.getUser(), findMember);
        memberRepository.save(updateMember);
        log.info("----------------------------------------------------------------");
        log.info("수정된 사용자 DB 저장 완료");

        ResponseMember.UserInfo userInfo = memberRepository.getUpdatedMemberInfo(updateMember);
        userInfo.setToken(jwt.replace("Bearer ", ""));
        log.info("현재 사용자 accessToken 저장");

        return new ResponseMember(userInfo);
    }

    @Override
    public ResponseProfile getProfile(String username, String userEmail) {
        Member findMember = memberRepository.findByUsername(username);
        ResponseProfile.ProfileInfo profile = memberRepository.getMemberProfile(findMember);
        log.info("----------------------------------------------------------------");
        log.info("조회한 사용자 정보로 Profile 생성");

        Member loginMember = memberRepository.findByEmail(userEmail);
        log.info("로그인한 사용자 정보 조회");

        return new ResponseProfile(memberRepository.setFollowState(profile, loginMember.getFollowList()));
    }

    @Override
    public ResponseProfile addFollow(String username, String userEmail) {
        Member findMember = memberRepository.findByUsername(username);
        ResponseProfile.ProfileInfo profile = memberRepository.getMemberProfile(findMember);
        log.info("----------------------------------------------------------------");
        log.info("조회한 사용자 정보로 Profile 생성");

        Member loginMember = memberRepository.findByEmail(userEmail);
        Member updateMember = memberRepository.addFollow(loginMember, username);
        log.info("로그인 사용자의 팔로우 목록 추가");

        memberRepository.save(updateMember);
        log.info("로그인 사용자의 수정 정보 저장");

        return new ResponseProfile(memberRepository.setFollowState(profile, updateMember.getFollowList()));
    }

    @Override
    public ResponseProfile delFollow(String username, String userEmail) {
        Member findMember = memberRepository.findByUsername(username);
        ResponseProfile.ProfileInfo profile = memberRepository.getMemberProfile(findMember);
        log.info("----------------------------------------------------------------");
        log.info("조회한 사용자 정보로 Profile 생성");

        Member loginMember = memberRepository.findByEmail(userEmail);
        Member updateMember = memberRepository.delFollow(loginMember, username);
        log.info("로그인 사용자의 팔로우 목록 제거");

        memberRepository.save(updateMember);
        log.info("로그인 사용자의 수정 정보 저장");

        return new ResponseProfile(memberRepository.setFollowState(profile, updateMember.getFollowList()));
    }

}
