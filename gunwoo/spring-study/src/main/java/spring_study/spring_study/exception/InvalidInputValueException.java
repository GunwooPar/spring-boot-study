package spring_study.spring_study.exception;

public class InvalidInputValueException extends CustomException {

    public InvalidInputValueException(String message) {
        super(ErrorCode.INVALID_INPUT_VALUE);
    }
}
