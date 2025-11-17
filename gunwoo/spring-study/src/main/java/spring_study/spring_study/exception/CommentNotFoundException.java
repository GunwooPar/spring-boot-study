package spring_study.spring_study.exception;


public class CommentNotFoundException extends CustomException {

    public CommentNotFoundException() {
        super(ErrorCode.COMMENT_NOT_FOUND);
    }



}
