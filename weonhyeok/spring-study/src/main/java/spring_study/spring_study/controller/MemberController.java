package spring_study.spring_study.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import spring_study.spring_study.domain.Member;
import spring_study.spring_study.dto.MemberEditRequest;
import spring_study.spring_study.dto.MemberJoinRequest;
import spring_study.spring_study.service.MemberService;

import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
@RequestMapping("/weonhyeok")
public class MemberController {
    private final MemberService memberService;

    @GetMapping({"", "/"})
    public String home(Model model) {
        model.addAttribute("members", memberService.findAll());
        return "index";
    }

    @PostMapping("/create")
    public String join(@ModelAttribute MemberJoinRequest req) {
        memberService.join(new Member(req.userName(), req.userAge(), LocalDateTime.now()));
        return "redirect:/weonhyeok";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        memberService.delete(id);
        return "redirect:/weonhyeok";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        Member member = memberService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("없는 회원 ID입니다." + id));
        model.addAttribute("member", member);
        return "edit";
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable Integer id, @ModelAttribute MemberEditRequest req){
        memberService.update(id,req.userName(), req.userAge());
        return "redirect:/weonhyeok";
    }
}
