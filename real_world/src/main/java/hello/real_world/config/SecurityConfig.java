package hello.real_world.config;

import hello.real_world.security.JwtAuthenticationFilter;
import hello.real_world.security.JwtUtilImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
                                .requestMatchers("/users").permitAll()                             // 사용자 등록 (권한 필요x)
                                .requestMatchers("/users/login").permitAll()                       // 사용자 로그인 (권한 필요x)
                                .requestMatchers("/user").hasAnyAuthority("USER")       // 사용자 정보 수정 (권한 필요)
                                .requestMatchers("/profiles/{username}").permitAll()
                                .requestMatchers("/profiles/{username}/follow").hasAnyAuthority("USER")
                                .anyRequest().authenticated()                         // 그 밖의 요청에 대해서 인증이 필요
                );

        // JWT 인증을 위해 구현한 JwtAuthenticationFilter 를 UsernamePasswordAuthenticationFilter 전에 수행하도록 설정
        httpSecurity
                .addFilterBefore(new JwtAuthenticationFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();

    }

}
