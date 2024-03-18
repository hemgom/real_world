package hello.real_world.service;

import hello.real_world.domain.member.Member;
import hello.real_world.dto.JwtInfo;
import hello.real_world.dto.RequestAddMember;
import hello.real_world.dto.RequestLogin;
import hello.real_world.dto.ResponseMember;
import hello.real_world.repository.MemberRepository;
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
        RequestAddMember.JoinInfo joinInfo = RequestAddMember.JoinInfo.addUserInfo(request);
        memberRepository.save(Member.addMemberInfo(joinInfo));
        return new ResponseMember(ResponseMember.UserInfo.getJoinInfo(joinInfo));
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

}
