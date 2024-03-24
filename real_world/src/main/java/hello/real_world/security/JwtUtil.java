package hello.real_world.security;

import org.springframework.security.core.Authentication;

public interface JwtUtil {
    JwtInfo createToken(String userInfo);

    boolean validateToken(String token);

    Authentication getAuthentication(String accessToken);
}
