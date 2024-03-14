package hello.real_world.security;

import hello.real_world.dto.JwtInfo;
import org.springframework.security.core.Authentication;

public interface JwtUtil {
    JwtInfo createToken(String userInfo);

    boolean validateToken(String token);

    Authentication getAuthentication(String accessToken);
}
