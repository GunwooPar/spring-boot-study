package spring_study.spring_study.exception;

/**
 *  질문을 찾을 수 없거나, 존재하지 않은 질문을 검색하고자 할 때 발생하는 예외.
 */
public class QuestionNotFoundException extends QuestionException{
    public QuestionNotFoundException(ExceptionMessageEnum error) {
        super(error.getErrorMessage(),error.getHttpStatus());
    }
}
