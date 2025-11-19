package spring_study.spring_study.exception.answer_exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;

@ControllerAdvice
@Slf4j
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class GlobalAnswerExceptionHandler {
    @ExceptionHandler(AnswerException.class)
    public String handleAnswerException(AnswerException e, Model model) {
        log.error("AnswerException 발생: {}",e.getMessage(),e);

        model.addAttribute("errorMessage",e.getMessage());
        model.addAttribute("errorCode",e.getHttpStatus());
        model.addAttribute("timestamp", LocalDateTime.now());
        return "error";
    }
}
