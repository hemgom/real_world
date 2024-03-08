package hello.real_world.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import hello.real_world.domain.member.Member;
import hello.real_world.dto.MemberUpdateDto;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static hello.real_world.domain.member.QMember.member;

@Repository
@Transactional
public class MemberRepositoryImpl implements MemberRepository {

    private final EntityManager em;
    private final JPAQueryFactory query;

    public MemberRepositoryImpl(EntityManager em) {
        this.em = em;
        this.query = new JPAQueryFactory(em);
    }

    @Override
    public Member save(Member member) {
        em.persist(member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        Member member = em.find(Member.class, id);
        return Optional.ofNullable(member);
    }

    @Override
    public void update(Long id, MemberUpdateDto updateParam) {
        Member findMember = em.find(Member.class, id);
        findMember.setEmail(updateParam.getEmail());
        findMember.setPassword(updateParam.getPassword());
    }

    @Override
    public List<Member> findAll(MemberSearchCond cond) {

        String user = cond.getUsername();

        return query
                .select(member)
                .from(member)
                .fetch();
    }
}
