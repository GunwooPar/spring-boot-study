package spring_study.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String root(){
        //redirect : 요청 처리 끝난 후 주소 변경 지시
        return "redirect:/question/list";
    }

}

