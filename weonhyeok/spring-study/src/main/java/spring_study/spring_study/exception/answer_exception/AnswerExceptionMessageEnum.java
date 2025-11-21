package spring_study.spring_study.exception.answer_exception;

import org.springframework.http.HttpStatus;

public enum AnswerExceptionMessageEnum {
    //400
    BLANK_ANSWER_CONTENT_EXCEPTION("답변 내용이 비어있거나 null입니다.",HttpStatus.BAD_REQUEST);

    private final String errorMessage;
    private final HttpStatus httpStatus;

    AnswerExceptionMessageEnum(String errorMessage, HttpStatus httpStatus) {
        this.errorMessage = errorMessage;
        this.httpStatus = httpStatus;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
