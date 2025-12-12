package spring_study.spring_study.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import spring_study.spring_study.domain.Question;
import spring_study.spring_study.dto.AnswerRequestDTO;
import spring_study.spring_study.dto.QuestionRequestDTO;
import spring_study.spring_study.dto.QuestionResponseDTO;
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
    public String questionList(Model model, @RequestParam(value = "page", defaultValue = "0") int page) {
        Page<Question> paging = questionService.getList(page);
        model.addAttribute("paging", paging);
        return "question_list";
    }

    //ID 값 PathVariable로 가져옴
    @GetMapping(value = "/detail/{id}")
    public String detailQuestionPage(Model model, @PathVariable Long id, AnswerRequestDTO answerRequestDTO) {
        QuestionResponseDTO question = questionService.getQuestionDto(id);
        model.addAttribute("question", question);
        return "question_detail";
    }

    /**
     * 질문 생성 페이지 GET 요청 Mapping
     *
     * @param request
     * @return question_create 페이지로 viewResolver 전송
     */
    @GetMapping("/new")
    public String returnCreateQuestionPage(QuestionRequestDTO request) {
        return "question_create";
    }


    /**
     * 질문 등록 POST 요청 (입력창에 질문제목, 질문 내용)을 DTO에 매칭
     * 데이터 바인딩 검증
     *
     * @param newQuestion   질문 등록 요청한 subject, content DTO로 변환
     * @param bindingResult 바인딩 리설트 없을 때 400 Bad Request 예외를 던짐
     *                      던진 예외는 thymeleaf에서 받음
     * @return 질문 리스트 페이지로 리다이렉트
     */
    @PostMapping
    public String questionCreate(@Valid QuestionRequestDTO newQuestion, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "question_create";
        }
        questionService.create(newQuestion.subject(), newQuestion.content());
        return "redirect:/question/list";
    }

}
