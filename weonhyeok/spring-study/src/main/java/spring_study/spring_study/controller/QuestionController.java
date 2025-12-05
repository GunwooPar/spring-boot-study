package spring_study.spring_study.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import spring_study.spring_study.dto.QuestionRequest;
import spring_study.spring_study.dto.QuestionResponse;
import spring_study.spring_study.service.QuestionService;

import java.util.List;

@Controller
@RequestMapping("/question")
@Slf4j
public class QuestionController {
    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    //localhost:8080/question 들어갈 시 질문 목록 페이지
    @GetMapping({"", "/"})
    public String home() {
        return "redirect:/question/list";
    }

    @GetMapping("/list")
    public String questionList(Model model) {
        List<QuestionResponse> questionList = questionService.getQuestionList();
        model.addAttribute("questionResponseList", questionList);
        return "question_list";
    }

    //ID 값 PathVariable로 가져옴
    @GetMapping(value = "/detail/{id}")
    public String detailQuestionPage(Model model, @PathVariable Long id) {
        QuestionResponse question = questionService.getQuestionDto(id);
        model.addAttribute("question", question);
        return "question_detail";
    }

    /**
     * 질문 생성 페이지 GET 요청 Mapping
     * @param request
     * @return question_create 페이지로 viewResolver 전송
     */
    @GetMapping("/new/q")
    public String returnCreateQuestionPage(QuestionRequest request) {
        return "question_create";
    }

    /**
     * 질문 등록 POST 요청 (입력창에 질문제목, 질문 내용)을 DTO에 매칭
     * @param newQuestion 질문 등록 요청한 subject, content DTO로 변환
     * @param bindingResult
     * @return 질문 리스트 페이지로 리다이렉트
     */
    @PostMapping("/new/q")
    public String questionCreate(@Valid QuestionRequest newQuestion, BindingResult bindingResult) {
        log.info(String.valueOf(newQuestion));
        if(bindingResult.hasErrors()) {
            return "question_create";
        }
        questionService.create(newQuestion.subject(), newQuestion.content());
        return "redirect:/question/list";
    }

}
