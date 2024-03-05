package hello.real_world.config;

import hello.real_world.repository.MemberRepository;
import hello.real_world.repository.MemberRepositoryImpl;
import hello.real_world.service.MemberService;
import hello.real_world.service.MemberServiceImpl;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class Config {

    private final EntityManager em;

    @Bean
    public MemberService memberService() {
        return new MemberServiceImpl(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository() {
        return new MemberRepositoryImpl(em);
    }
}
