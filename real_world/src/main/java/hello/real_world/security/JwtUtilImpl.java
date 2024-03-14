package hello.real_world.security;

import hello.real_world.dto.JwtInfo;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JwtUtilImpl implements JwtUtil {

    private final Key key;
    private final long accessTokenValidTime;
    private final long refreshTokenValidTime;

    public JwtUtilImpl(@Value("${jwt.secret}") String secretKey,
                       @Value("${jwt.accessToken_valid_time}") long accessTokenValidTime,
                       @Value("${jwt.refreshToken_valid_time}") long refreshTokenValidTime) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.accessTokenValidTime = accessTokenValidTime;
        this.refreshTokenValidTime = refreshTokenValidTime;
    }

    // 인증 정보(사용자 ID/PW)로 AccessToken, RefreshToken 을 생성하는 메서드
    @Override
    public JwtInfo createToken(String userInfo) {

        long now = (new Date()).getTime();

        // AccessToken 생성
        Date accessTokenExpiresTime = new Date(now + accessTokenValidTime);    // accessToken 만료 시간 설정
        String accessToken = Jwts.builder()
                .setSubject(userInfo)
                .claim("auth", "USER")
                .setExpiration(accessTokenExpiresTime)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        // refreshToken 생성
        Date refreshTokenExpiresTime = new Date(now + refreshTokenValidTime);   // refreshToken 만료 시간 설정
        String refreshToken = Jwts.builder()
                .setExpiration(refreshTokenExpiresTime)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        return JwtInfo.builder()
                .grantType("Bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();

    }

    // 토큰 검증 메서드
    @Override
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT", e);
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT", e);
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT", e);
        } catch (IllegalArgumentException e) {
            log.info("JWT claims string is empty", e);
        }
        return false;
    }

    // JWT 를 복호화해 토큰에 저장된 정보를 꺼내는 메서드
    @Override
    public Authentication getAuthentication(String accessToken) {

        // JWT 복호화
        Claims claims = parseClaims(accessToken);

        if (claims.get("auth") == null) {
            throw new RuntimeException("권한 정보가 없는 토큰 입니다.");
        }

        // Claims 에서 권한 정보 가져오기
        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get("auth").toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .toList();

        UserDetails principal = new User(claims.getSubject(), "", authorities);
        log.info("Check principal = {}", principal);
        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }

    private Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(accessToken)    // 토큰 검증, 파싱 모두 수행
                    .getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

}
