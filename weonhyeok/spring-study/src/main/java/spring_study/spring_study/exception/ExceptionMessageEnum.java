package spring_study.spring_study.exception;

import org.springframework.http.HttpStatus;

public enum ExceptionMessageEnum {
    NO_DATA_EXCEPTION("질문글을 찾을 수 없거나 질문글이 존재하지 않습니다.",HttpStatus.NOT_FOUND);

    private final String errorMessage;
    private final HttpStatus httpStatus;

    ExceptionMessageEnum(String errorMessage, HttpStatus httpStatus) {
        this.errorMessage = errorMessage;
        this.httpStatus = httpStatus;
    }

    public String getErrorMessage(){
        return errorMessage;
    }

    public HttpStatus getHttpStatus(){
        return httpStatus;
    }
}
