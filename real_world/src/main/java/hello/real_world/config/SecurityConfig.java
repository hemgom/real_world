package hello.real_world.config;

import hello.real_world.web.JwtAuthenticationFilter;
import hello.real_world.web.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtProvider jwtProvider;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable);     // REST API 이므로 crsf 보안 사용x

        httpSecurity
                .authorizeHttpRequests(
                        authorize -> authorize
                            .requestMatchers("/members/sign-in").permitAll()    // 해당 API 에 대해선 모든 요청 허가
                            .requestMatchers("/members/test").hasRole("USER")   // USER 권한이 있어야 요청 가능
                            .anyRequest().authenticated()                               // 이 밖의 모든 요청에 대해 인증 필요
                );

        
        // JWT 인증을 위해 구현 필터를 UsernamePasswordAuthenticationFilter 전에 실행
        httpSecurity
                .addFilterBefore(new JwtAuthenticationFilter(jwtProvider), UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // BCrypt Encoder
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
