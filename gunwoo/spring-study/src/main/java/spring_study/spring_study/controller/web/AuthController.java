package spring_study.spring_study.controller.web;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import spring_study.spring_study.domain.User;
import spring_study.spring_study.repository.UserRepository;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/signup")
    public String signupPage() {
        return "signup";
    }

    @PostMapping("/signup")
    public String signup(@RequestParam String username,
                         @RequestParam String password,
                         RedirectAttributes redirectAttributes) {
                         @RequestParam String password) {

        // 중복 체크
        if (userRepository.existsByUsername(username)) {
            return "redirect:/signup?error=duplicate";
        }

        // 비밀번호 암호화 후 저장
        User user = User.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .role(User.Role.USER)
                .build();

        userRepository.save(user);

        redirectAttributes.addAttribute("successMessage", "회원가입이 완료되었습니다.");

        return "redirect:/login";
        return "redirect:/login?signup=success";
    }
}