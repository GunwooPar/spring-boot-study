package spring_study.spring_study.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import spring_study.spring_study.domain.Question;
import spring_study.spring_study.dto.AnswerRequest;
import spring_study.spring_study.service.AnswerService;
import spring_study.spring_study.service.QuestionService;

import java.util.Map;

@Controller
@RequestMapping("/answer")
@RequiredArgsConstructor
public class AnswerController {
    private final QuestionService questionService;
    private final AnswerService answerService;

    /**
     * textarea name 속성값과 AnswerRequest dto 필드명 값이 동일해 자동 바인딩
     * 서비스 계층을 통해 답변을 등록해주고 다시 답변달린 질문 상세 페이지로 리다이렉트
     * @param id 질문 pk id값
     * @param answerRequest 답변 요청 객체 dto
     * @return create/{id} post 요청했던 질문 상세 페이지로 다시 리다이렉트
     */
    @PostMapping("/create/{id}")
    public String createAnswer(Model model, @PathVariable Long id,
                               AnswerRequest answerRequest) {
        Question question = questionService.getQuestion(id);
        answerService.createAnswer(question, answerRequest.content());
        return String.format("redirect:/question/detail/%s", id);
    }

}
