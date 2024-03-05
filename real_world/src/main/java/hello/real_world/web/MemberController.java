package hello.real_world.web;

import hello.real_world.domain.member.Member;
import hello.real_world.repository.MemberSearchCond;
import hello.real_world.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping
    public String members(@ModelAttribute("itemSearch") MemberSearchCond memberSearch, Model model) {
        List<Member> members = memberService.findMember(memberSearch);
        model.addAttribute("member", members);
        return "members";
    }
}
