package spring_study.spring_study.exception.answer_exception;

import org.springframework.http.HttpStatus;

public class AnswerException extends RuntimeException {
    private final HttpStatus httpStatus;

    public AnswerException(String errorMessage, HttpStatus httpStatus) {
        super(errorMessage);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
