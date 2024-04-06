package hello.real_world.config;

import hello.real_world.security.JwtAuthenticationFilter;
import hello.real_world.security.JwtUtilImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtUtilImpl jwtUtil;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

        // basic auth 및 csrf 보안 기능 off
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable);

        httpSecurity
                .authorizeHttpRequests(
                        authorize -> authorize
                                // 사용자 등록
                                .requestMatchers(HttpMethod.POST, "/users").permitAll()
                                // 로그인
                                .requestMatchers(HttpMethod.POST, "/users/login").permitAll()
                                // 현재 로그인한 사용자 정보 조회
                                .requestMatchers(HttpMethod.GET, "/user").hasAnyAuthority("USER")
                                // 사용자 정보 수정
                                .requestMatchers(HttpMethod.PUT, "/user").hasAnyAuthority("USER")
                                // 사용자 프로필 조회
                                .requestMatchers(HttpMethod.GET, "/profiles/{username}").permitAll()
                                // 팔로우 추가
                                .requestMatchers(HttpMethod.POST, "/profiles/{username}/follow").hasAnyAuthority("USER")
                                // 팔로우 삭제
                                .requestMatchers(HttpMethod.DELETE, "/profiles/{username}/follow").hasAnyAuthority("USER")
                                // 기사 등록
                                .requestMatchers(HttpMethod.POST, "/articles").hasAnyAuthority("USER")
                                // 기사 조회
                                .requestMatchers(HttpMethod.GET, "/articles/{slug}").permitAll()
                                // 기사 수정
                                .requestMatchers(HttpMethod.PUT, "/articles/{slug}").hasAnyAuthority("USER")
                                // 기사 삭제
                                .requestMatchers(HttpMethod.DELETE, "/articles/{slug}").hasAnyAuthority("USER")
                                // 즐겨찾기 추가
                                .requestMatchers(HttpMethod.POST, "/articles/{slug}/favorite").hasAnyAuthority("USER")
                                // 즐겨찾기 삭제
                                .requestMatchers(HttpMethod.DELETE, "/articles/{slug}/favorite").hasAnyAuthority("USER")
                                // 태그 조회
                                .requestMatchers(HttpMethod.GET, "/tags").permitAll()
                                // 댓글 추가
                                .requestMatchers(HttpMethod.POST, "/articles/{slug}/comments").hasAnyAuthority("USER")
                                // 댓글 삭제
                                .requestMatchers(HttpMethod.DELETE, "/articles/{slug}/comments/{id}").hasAnyAuthority("USER")
                                // 기사에 달린 모든 댓글 조회
                                .requestMatchers(HttpMethod.GET, "/articles/{slug}/comments").permitAll()
                                // 최근 기사 목록 조회
                                .requestMatchers(HttpMethod.GET, "/articles").permitAll()
                                .anyRequest().permitAll()   // 위에 설정한 요청 외에는 인증 필요 없음
                );

        // JWT 인증을 위해 구현한 JwtAuthenticationFilter 를 UsernamePasswordAuthenticationFilter 전에 수행하도록 설정
        httpSecurity
                .addFilterBefore(new JwtAuthenticationFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();

    }

}
