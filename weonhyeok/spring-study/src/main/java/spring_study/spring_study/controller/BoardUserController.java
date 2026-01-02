package spring_study.spring_study.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import spring_study.spring_study.dto.user_dto.BoardUserRequestDTO;
import spring_study.spring_study.service.BoardUserService;

@Controller
@RequestMapping("/user")
public class BoardUserController {

    private final BoardUserService userService;

    @Autowired
    public BoardUserController(BoardUserService userService) {
        this.userService = userService;
    }

    @GetMapping("/signup")
    public String signup(BoardUserRequestDTO userRequestDTO) {
        return "signup_page";
    }

    @PostMapping("/signup")
    public String signup(@Valid BoardUserRequestDTO userRequestDTO, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "/signup_page";
        }
        //비밀번호가 다르다면 바인딩 리설트에 경고문자 넣어서 보내기
        if(!userRequestDTO.userPassword().equals(userRequestDTO.userPasswordCheck())) {
            bindingResult.rejectValue("userPasswordCheck","passwordInCorrect","2개의 비밀번호가 일치하지 않습니다.");
            return "/signup_page";
        }

        userService.create(userRequestDTO.userId(), userRequestDTO.userPassword(), userRequestDTO.userEmail());
        return "redirect:/";
    }
}
