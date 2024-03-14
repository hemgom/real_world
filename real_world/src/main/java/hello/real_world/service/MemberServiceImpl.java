package hello.real_world.service;

import hello.real_world.domain.member.Member;
import hello.real_world.dto.JwtInfo;
import hello.real_world.dto.RequestAddMember;
import hello.real_world.dto.RequestLogin;
import hello.real_world.dto.ResponseMember;
import hello.real_world.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

// 'MemberService' 구현체 클래스, 대부분 기능을 'repository' 에 위임
@Slf4j
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Override
    public ResponseMember save(RequestAddMember request) {
        RequestAddMember.JoinInfo joinInfo = RequestAddMember.JoinInfo.addUserInfo(request);
        memberRepository.save(Member.addMemberInfo(joinInfo));
        return new ResponseMember(ResponseMember.UserInfo.getJoinInfo(joinInfo));
    }

    @Override
    public ResponseMember userLogin(RequestLogin request) {
        ResponseMember.UserInfo userInfo = memberRepository.checkLoginInfo(request);
        log.info("loginUserEmail = {}", userInfo.getEmail());
        log.info("loginUserToken = {}", userInfo.getToken());

        JwtInfo jwtInfo = memberRepository.createToken(String.format("%s:%s", userInfo.getEmail(), userInfo.getUsername()));
        log.info("CreateCheck: accessToken = {}", jwtInfo.getAccessToken());

        userInfo.setToken(jwtInfo.getAccessToken());
        log.info("loginUserToken = {}", userInfo.getToken());

        return new ResponseMember(userInfo);
//        return memberRepository.checkLoginInfo(request);
    }

}
