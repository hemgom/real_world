package hello.real_world.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class MemberServiceTest {

    private final MemberRepositoryTest memberRepositoryTest;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtProvider jwtProvider;

    @Transactional
    public JwtDto singIn(String username, String password) {

        // username + password 를 통해 Authentication 객체 생성
        // 현 시점 authentication 은 authenticated 값이 false 임
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);

        // 실제 검증, authenticate() 를 통해 용청된 Member 에 대한 검증 진행
        // authenticate() 실행 시 CustomUserDetailService 에서 만든 loadUserByUsername() 실행
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 인증 정보를 통해 JWT 토큰 생성
        JwtDto jwtDto = jwtProvider.generateToken(authentication);

        return jwtDto;
    }
}
