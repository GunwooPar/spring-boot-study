package spring_study.spring_study.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import spring_study.spring_study.dto.QuestionRequest;
import spring_study.spring_study.dto.QuestionResponse;
import spring_study.spring_study.service.QuestionService;

@Controller
@RequestMapping("/answer")
@RequiredArgsConstructor
public class AnswerController {
    private final QuestionService questionService;

    @PostMapping("/create/{id}")
    public String createAnswer(Model model, @PathVariable Long id,
                               @RequestBody String content) {
        QuestionResponse question = questionService.getQuestion(id);
        //TODO: 답변 저장 기능 나중에 구현 예정
        return String.format("redirect:/question/detail/%s", id);
    }

}
