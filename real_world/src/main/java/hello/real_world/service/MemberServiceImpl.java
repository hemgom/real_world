package hello.real_world.service;

import hello.real_world.domain.member.Member;
import hello.real_world.dto.*;
import hello.real_world.repository.MemberRepository;
import hello.real_world.security.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        return new ResponseMember(ResponseMember.UserInfo.setUserInfo(joinInfo));
    }

    @Override
    public ResponseMember userLogin(RequestLogin request) {
        ResponseMember.UserInfo userInfo = memberRepository.checkLoginInfo(request);
        log.info("loginUserEmail = {}", userInfo.getEmail());
        log.info("loginUserToken = {}", userInfo.getToken());

        JwtInfo jwtInfo = jwtUtil.createToken(userInfo.getEmail());
        log.info("CreateCheck: accessToken = {}", jwtInfo.getAccessToken());

        userInfo.setToken(jwtInfo.getAccessToken());
        log.info("loginUserToken = {}", userInfo.getToken());

        return new ResponseMember(userInfo);
    }

    @Override
    public ResponseMember updateMember(RequestUpdateMember request, String jwt, Authentication authentication) {
        Member updateMember = memberRepository.updateMemberInfo(request, authentication);
        memberRepository.save(updateMember);
        ResponseMember.UserInfo userInfo = memberRepository.findMemberById(updateMember.getId());

        userInfo.setToken(jwt.replace("Bearer ", ""));
        return new ResponseMember(userInfo);
    }

    @Override
    public ResponseProfile getProfile(String username, Authentication authentication) {
        ResponseProfile.ProfileInfo profileInfo = memberRepository.getProfile(username);
        return memberRepository.checkFollow(authentication, profileInfo);
    }

}
