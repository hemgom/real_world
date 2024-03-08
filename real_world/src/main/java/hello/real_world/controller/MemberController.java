package hello.real_world.controller;

import hello.real_world.domain.member.Member;
import hello.real_world.dto.UserDto;
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
    public UserDto addMember(@RequestBody UserDto user) {
        log.info("POST 사용자 등록 요청");
        Member member = user.getUser();
        log.info("input data : member = {}", member);
        return new UserDto(memberService.save(member));
    }

    @PostMapping("/login")
    public UserDto login(@RequestBody UserDto loginInfo) {
        log.info("POST 로그인 요청");
        return new UserDto(memberService.findMember(loginInfo));
    }

}
