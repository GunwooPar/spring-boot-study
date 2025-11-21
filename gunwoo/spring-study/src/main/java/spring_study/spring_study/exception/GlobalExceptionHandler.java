package spring_study.spring_study.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import spring_study.spring_study.dto.ErrorResponse;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(CustomException e) {

        ErrorCode errorCode = e.getErrorCode();

        ErrorResponse response = ErrorResponse.of(errorCode);

        return new ResponseEntity<>(response, errorCode.getHttpStatus());


    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ErrorResponse> handleBindException(BindException e) {

        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getFieldErrors()
                .forEach((fieldError)
                        -> errors.put(
                        fieldError.getField(), fieldError.getDefaultMessage())
                );

        ErrorResponse response = ErrorResponse.of(
                ErrorCode.INVALID_INPUT_VALUE,
                errors.toString()
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }


}
