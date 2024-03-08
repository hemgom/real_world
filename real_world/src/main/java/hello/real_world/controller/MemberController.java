package hello.real_world.controller;

import hello.real_world.domain.member.Member;
import hello.real_world.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    // 사용자 추가
    @PostMapping
    public Member addMember(@RequestBody Member member) {
        log.info("POST /users HTTP 요청");
        return memberService.save(member);
    }
}
