package spring_study.spring_study.dto;

import spring_study.spring_study.exception.ErrorCode;
import lombok.NonNull;

public record ErrorResponse (
    int httpStatus,
    String message
) {
    public static ErrorResponse of(@NonNull ErrorCode errorCode) {
        return new ErrorResponse(
                errorCode.getHttpStatus().value(),
                errorCode.getMessage()

        );
    }

    public static ErrorResponse of(@NonNull ErrorCode errorCode, @NonNull String detailMessage) {
        return new ErrorResponse(
                errorCode.getHttpStatus().value(),
                detailMessage
        );
    }
}
