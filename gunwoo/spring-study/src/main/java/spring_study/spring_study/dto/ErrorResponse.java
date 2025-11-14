package spring_study.spring_study.dto;


import spring_study.spring_study.exception.ErrorCode;

public record ErrorResponse (
    int httpStatus,
    String message
) {
    public static ErrorResponse of(ErrorCode errorCode) {
        return new ErrorResponse(
                errorCode.getHttpStatus().value(),
                errorCode.getMessage()
        );
    }
}
