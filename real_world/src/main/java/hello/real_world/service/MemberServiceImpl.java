package hello.real_world.service;

import hello.real_world.domain.member.Member;
import hello.real_world.dto.UserDto;
import hello.real_world.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
    public Member findMember(UserDto loginInfo) {
        return memberRepository.findMember(loginInfo);
    }
}
