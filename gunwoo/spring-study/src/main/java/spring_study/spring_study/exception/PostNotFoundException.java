package spring_study.spring_study.exception;

public class PostNotFoundException extends CustomException {
    public PostNotFoundException() {
        super(ErrorCode.POST_NOT_FOUND);
    }
}
