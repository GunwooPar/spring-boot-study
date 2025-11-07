package spring_study.spring_study.controller;

import org.springframework.ui.Model;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import spring_study.spring_study.domain.Member;
import spring_study.spring_study.dto.MemberJoinRequest;
import spring_study.spring_study.dto.MemberLoginRequest;
import spring_study.spring_study.service.MemberService;

import java.time.LocalDateTime;

/**
 * join, login Controller
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/weonhyeok")
public class AuthController {
    private final MemberService memberService;

    @GetMapping({"", "/"})
    public String loginForm(){
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute MemberLoginRequest req, Model model){
        Member loginMember = memberService.login(req.userName(), req.password());

        // 관리자 계정이면 서비스 전체 사용자 반환
        if("ADMIN".equals(loginMember.getRole())) {
            model.addAttribute("members",memberService.findAll());
            return "admin";
        }
        model.addAttribute("member",loginMember);
        return "user";
    }

    @GetMapping("/join")
    public String joinForm() {
        return "join";
    }

    @PostMapping("/join")
    public String join(@ModelAttribute MemberJoinRequest req) {
        Member member = new Member(
                req.userName(),
                req.password(),
                req.userAge(),
                checkAdmin(req.userName()),
                LocalDateTime.now()
        );
        memberService.join(member);
        return "redirect:/weonhyeok";
    }

    private String checkAdmin(String userName){
        if(userName.equals("ADMIN")){
            return "ADMIN";
        }
        return "USER";
    }
}
