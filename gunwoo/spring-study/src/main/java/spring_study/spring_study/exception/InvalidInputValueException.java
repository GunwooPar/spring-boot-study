package spring_study.spring_study.exception;

public class InvalidInputValueException extends CustomException {

    public InvalidInputValueException() {
        super(ErrorCode.INVALID_INPUT_VALUE);
    }
}
