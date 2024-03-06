package hello.real_world.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberControllerTest {

    private final MemberServiceTest memberServiceTest;

    @PostMapping("/sign-in")
    public JwtDto signIn(@RequestBody SignInDto signInDto) {
        String username = signInDto.getUsername();
        String password = signInDto.getPassword();
        JwtDto jwtDto = memberServiceTest.singIn(username, password);
        log.info("request username = {}, password = {}", username, password);
        log.info("jwtDto accessToken = {}, refreshToken = {}", jwtDto.getAccessToken(), jwtDto.getRefreshToken());
        return jwtDto;
    }

    @PostMapping("/test")
    public String test() {
        return "success";
    }
}
