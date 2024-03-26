package hello.real_world.domain.member.controller;

import hello.real_world.domain.member.dto.*;
import hello.real_world.domain.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    // 사용자 추가
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/users")
    public ResponseMember addMember(@RequestBody RequestAddMember request) {
        log.info("POST 사용자 등록 요청");
        return memberService.save(request);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping("/users/login")
    public ResponseMember login(@Valid @RequestBody RequestLogin request) {
        log.info("POST 로그인 요청");
        return memberService.userLogin(request);
    }

    @ResponseStatus(HttpStatus.RESET_CONTENT)
    @PutMapping("/user")
    public ResponseMember updateMember(@RequestBody RequestUpdateMember request,
                                       @RequestHeader("Authorization") String jwt,
                                       Authentication authentication ) {
        log.info("PUT 사용자 정보 수정");
        String userEmail = authentication.getName();
        return memberService.updateMember(request, jwt, userEmail);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @GetMapping("/profiles/{username}")
    public ResponseProfile getProfile(@PathVariable("username") String username,
                                      Authentication authentication) {
        log.info("GET 사용자 프로필 요청");
        String userEmail = authentication.getName();
        return memberService.getProfile(username, userEmail);
    }

    @ResponseStatus(HttpStatus.RESET_CONTENT)
    @PostMapping("/profiles/{username}/follow")
    public ResponseProfile addFollow(@PathVariable("username") String username,
                                     Authentication authentication) {
        log.info("POST 해당 사용자 팔로우 추가");
        String userEmail = authentication.getName();
        return memberService.addFollow(username, userEmail);
    }

    @ResponseStatus(HttpStatus.RESET_CONTENT)
    @DeleteMapping("/profiles/{username}/follow")
    public ResponseProfile delFollow(@PathVariable("username") String username,
                                     Authentication authentication) {
        log.info("DELETE 해당 사용자 팔로우 해제");
        String userEmail = authentication.getName();
        return memberService.delFollow(username, userEmail);
    }

}
