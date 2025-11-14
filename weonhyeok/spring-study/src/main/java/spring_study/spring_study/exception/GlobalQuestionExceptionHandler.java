package spring_study.spring_study.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalQuestionExceptionHandler {
    @ExceptionHandler(QuestionException.class)
    public String handleMemberException(QuestionException e, Model model) {
        model.addAttribute("errorMessage", e.getMessage());
        return "error";
    }
}
