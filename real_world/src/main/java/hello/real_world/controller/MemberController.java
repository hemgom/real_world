package hello.real_world.controller;

import hello.real_world.dto.*;
import hello.real_world.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    // 사용자 추가
    @PostMapping("/users")
    public ResponseMember addMember(@RequestBody RequestAddMember request) {
        log.info("POST 사용자 등록 요청");
        return memberService.save(request);
    }

    @PostMapping("/users/login")
    public ResponseMember login(@RequestBody RequestLogin request) {
        log.info("POST 로그인 요청");
        return memberService.userLogin(request);
    }

    @PutMapping("/user")
    public ResponseMember updateMember(@RequestBody RequestUpdateMember request,
                                       @RequestHeader("Authorization") String jwt,
                                       Authentication authentication ) {
        log.info("PUT 사용자 정보 수정");
        return memberService.updateMember(request, jwt, authentication);
    }

    @GetMapping("/profiles/{username}")
    public ResponseProfile getProfile(@PathVariable("username") String username,
                                       Authentication authentication) {
        log.info("GET 사용자 프로필 요청");
        return memberService.getProfile(username, authentication);
    }

    @PostMapping("/profiles/{username}/follow")
    public ResponseProfile addFollow(@PathVariable("username") String username,
                                     Authentication authentication) {
        log.info("POST 해당 사용자 팔로우 추가");
        return memberService.addFollow(username, authentication);
    }

    @DeleteMapping("/profiles/{username}/follow")
    public ResponseProfile delFollow(@PathVariable("username") String username,
                                     Authentication authentication) {
        log.info("DELETE 해당 사용자 팔로우 해제");
        return memberService.delFollow(username, authentication);
    }

}
