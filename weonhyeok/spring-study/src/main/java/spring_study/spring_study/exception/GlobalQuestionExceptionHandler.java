package spring_study.spring_study.exception;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Controller
public class GlobalQuestionExceptionHandler {
    @ExceptionHandler(QuestionException.class)
    public String handleMemberException(QuestionException e, Model model) {
        model.addAttribute("errorMessage", e.getMessage());
        return "error";
    }
}
