package spring_study.spring_study.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *  질문을 찾을 수 없거나, 존재하지 않은 질문을 검색하고자 할 때 발생하는 예외.
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "entity not found")
public class QuestionNotFoundException extends QuestionException{
    public QuestionNotFoundException(ExceptionMessageEnum error) {
        super(error.getErrorMessage());
    }
}
