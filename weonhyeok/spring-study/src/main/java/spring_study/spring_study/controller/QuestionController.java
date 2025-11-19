package spring_study.spring_study.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import spring_study.spring_study.dto.QuestionResponse;
import spring_study.spring_study.service.QuestionService;

import java.util.List;

@Controller
@RequestMapping("/question")
public class QuestionController {
    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    //localhost:8080/question 들어갈 시 질문 목록 페이지
    @GetMapping({"","/"})
    public String home() {
        return "redirect:/question/list";
    }

    @GetMapping("/list")
    public String questionList(Model model) {
        List<QuestionResponse> questionList = questionService.getQuestionList();
        model.addAttribute("questionResponseList",questionList);
        return "question_list";
    }

    //ID 값 PathVariable로 가져옴
    @GetMapping(value = "/detail/{id}")
    public String detailQuestionPage(Model model, @PathVariable Long id) {
        QuestionResponse question = questionService.getQuestionDto(id);
        model.addAttribute("question",question);
        return "question_detail";
    }

    @GetMapping("/create-question")
    public String createQuestion() {
        return "question_create";
    }
}
