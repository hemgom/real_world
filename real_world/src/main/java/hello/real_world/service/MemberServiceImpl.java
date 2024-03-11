package hello.real_world.service;

import hello.real_world.domain.member.Member;
import hello.real_world.dto.RequestAddMember;
import hello.real_world.dto.ResponseMember;
import hello.real_world.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

// 'MemberService' 구현체 클래스, 대부분 기능을 'repository' 에 위임
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Override
    public ResponseMember save(RequestAddMember request) {
        RequestAddMember.JoinInfo joinInfo = RequestAddMember.JoinInfo.addUserInfo(request);
        memberRepository.save(Member.addMemberInfo(joinInfo));
        return new ResponseMember(ResponseMember.UserInfo.getJoinInfo(joinInfo));
    }

    @Override
    public Member findMember(ResponseMember loginInfo) {
        return memberRepository.findMember(loginInfo);
    }

}
