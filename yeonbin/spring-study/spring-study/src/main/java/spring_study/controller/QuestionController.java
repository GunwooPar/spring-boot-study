package spring_study.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import spring_study.Service.QuestionService;
import spring_study.domain.Question;

import java.util.List;

/*@ResponseBody : 메소드가 반환하는 문자열이 뷰 이름X HTTP 응답본문 그 자체
* @RestController : @Controller + @ResponseBody*/
@RequestMapping("/question")
@RequiredArgsConstructor
@Controller
public class QuestionController {

    //리포지토리 대신 서비스 사용
    private final QuestionService questionService;

    @GetMapping("/list")
    public String List(Model model) { //moedel 객체가 자동 생성
        //서비스 호출
        List<Question> questionList = this.questionService.getList();
        model.addAttribute("questionList", questionList);
        return "question_list";
    }

    @GetMapping(value = "/detail/{id}")
    public String detail(Model model, @PathVariable("id") Long id) {
        Question question = this.questionService.getQuestion(id);
        model.addAttribute("question", question);
        return "question_detail";
    }
}
