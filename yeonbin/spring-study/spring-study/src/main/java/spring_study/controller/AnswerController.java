package spring_study.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import spring_study.Service.AnswerService;
import spring_study.Service.QuestionService;
import spring_study.domain.Question;
import spring_study.repository.AnswerRepository;

@RequestMapping("/answer")
@RequiredArgsConstructor
@Controller
public class AnswerController {

    private final QuestionService questionService;
    private final AnswerService answerService;

    @PostMapping("/create/{id}")
    //@RequestParam -> content라는 이름의 데이터를 찾아서 String 타입 변수 할당
    public String createAnswer(Model model, @PathVariable("id") Long id,
                               @RequestParam(value = "content") String content) {
        Question question = this.questionService.getQuestion(id);
        //답변 저장
        this.answerService.create(question, content);
        return String.format("redirect:/question/detail/%s", id);
    }
}
