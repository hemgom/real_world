package hello.real_world.config;

import hello.real_world.repository.MemberRepository;
import hello.real_world.repository.MemberRepositoryImpl;
import hello.real_world.service.MemberService;
import hello.real_world.service.MemberServiceImpl;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// MemberService 와 MemberRepository 가 어떤 구현체를 사용할지 설정하는 클래스 (Configuration)
// 의존 관계: Service -> Repository
@Configuration
@RequiredArgsConstructor
public class Config {

    private final EntityManager em;

    // Service 구현 객체 설정
    @Bean
    public MemberService memberService() {
        return new MemberServiceImpl(memberRepository());
    }

    // Repository 구현 객체 설정
    @Bean
    public MemberRepository memberRepository() {
        return new MemberRepositoryImpl(em);
    }

}
