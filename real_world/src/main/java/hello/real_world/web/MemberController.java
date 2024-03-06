package hello.real_world.web;

import hello.real_world.domain.member.Member;
import hello.real_world.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    @ResponseBody
    public Member addMember(@ModelAttribute Member user, RedirectAttributes redirectAttributes) {
        Member users = memberService.save(user);
        redirectAttributes.addAttribute("memberId", users.getId());
        redirectAttributes.addAttribute("status", true);
        return users;
    }
}
