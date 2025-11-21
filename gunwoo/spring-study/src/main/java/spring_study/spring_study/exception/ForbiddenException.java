package spring_study.spring_study.exception;

public class ForbiddenException extends CustomException {
    public ForbiddenException() {
        super(ErrorCode.FORBIDDEN);
    }
}
