package spring_study.spring_study.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import spring_study.spring_study.dto.QuestionRequest;
import spring_study.spring_study.dto.QuestionResponse;
import spring_study.spring_study.service.QuestionService;

import java.util.List;

@Controller
public class QuestionController {
    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping({"","/"})
    public String home() {
        return "redirect:/question/list";
    }

    @GetMapping("/question/list")
    public String questionList(Model model) {
        List<QuestionResponse> questionList = questionService.getQuestionList();
        model.addAttribute("questionResponseList",questionList);
        return "question_list";
    }

    @GetMapping(value = "/question/detail/{id}")
    public String detailQuestionPage(Model model, QuestionRequest questionRequest) {
        QuestionResponse question = questionService.getQuestion(questionRequest.id());
        model.addAttribute("question",question);
        return "question_detail";
    }
}
