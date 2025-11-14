package spring_study.spring_study.exception;

import org.springframework.http.HttpStatus;

/**
 * 모든 질문 페이지 예외 관련 질문 예외의 부모 예외이다.
 */
public class QuestionException extends RuntimeException{
    private final HttpStatus httpStatus;

    public QuestionException(String message, HttpStatus httpStatus){
        super(message);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getStatus() {
        return httpStatus;
    }
}
