package spring_study.spring_study.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import spring_study.spring_study.domain.Member;
import spring_study.spring_study.dto.MemberEditRequest;
import spring_study.spring_study.service.MemberService;

/**
 * edit, update, delete 위주 controller
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/weonhyeok")
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        Member member = memberService.findById(id);
        model.addAttribute("member", member);
        return "edit";
    }

    //나중에 ADMIN은 ADMIN으로 USER는 USER로 리다이렉트 구현
    @PostMapping("/update/{id}")
    public String update(@PathVariable Long id, @ModelAttribute MemberEditRequest req) {
        memberService.update(id, req.userName(), req.userAge());
        return "redirect:/weonhyeok";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        memberService.delete(id);
        return "redirect:/weonhyeok";
    }
}
