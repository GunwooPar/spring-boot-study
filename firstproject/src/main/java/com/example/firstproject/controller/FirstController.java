package com.example.firstproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FirstController {

    @GetMapping("/hi") //url 요청 접수
    public String niceToMeetYou(Model model) { // 모델 객체 받아오기
        model.addAttribute("username", "홍팍"); //model 객체가 변숫값을 변수명에 연결해 웹브라우저로 보냄
        return "greetings"; //greetings.mustache 파일(뷰 템플릿) 반환
    }

    @GetMapping("/bye")
    public String seeYouNext(Model model){
        model.addAttribute("nickname", "홍길동");
        return "goodbye";
    }
}
