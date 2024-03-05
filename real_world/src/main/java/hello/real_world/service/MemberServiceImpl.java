package hello.real_world.service;

import hello.real_world.domain.member.Member;
import hello.real_world.repository.MemberRepository;
import hello.real_world.repository.MemberSearchCond;
import hello.real_world.repository.MemberUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// 'MemberService' 구현체 클래스, 대부분 기능을 'repository' 에 위임
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Override
    public Member save(Member member) {
        return memberRepository.save(member);
    }

    @Override
    public void update(Long id, MemberUpdateDto updateParam) {
        memberRepository.update(id, updateParam);
    }

    @Override
    public Optional<Member> findById(Long id) {
        return memberRepository.findById(id);
    }

    @Override
    public List<Member> findMember(MemberSearchCond cond) {
        return memberRepository.findAll(cond);
    }
}
