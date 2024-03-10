package hello.real_world.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import hello.real_world.repository.MemberRepository;
import hello.real_world.repository.MemberRepositoryImpl;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// MemberService 와 MemberRepository 가 어떤 구현체를 사용할지 설정하는 클래스 (Configuration)
// 의존 관계: Service -> Repository
@Configuration
@RequiredArgsConstructor
public class Config {

    @Bean
    public JPAQueryFactory jpaQueryFactory(EntityManager em) {
        return new JPAQueryFactory(em);
    }

}
