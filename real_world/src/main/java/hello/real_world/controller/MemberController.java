package hello.real_world.controller;

import hello.real_world.dto.RequestAddMember;
import hello.real_world.dto.ResponseMember;
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
    public ResponseMember addMember(@RequestBody RequestAddMember request) {
        log.info("POST 사용자 등록 요청");
        return memberService.save(request);
    }

//    @PostMapping("/login")
//    public ResponseMember login(@RequestBody ResponseMember loginInfo) {
//        log.info("POST 로그인 요청");
//        return new ResponseMember(memberService.findMember(loginInfo));
//    }

}
