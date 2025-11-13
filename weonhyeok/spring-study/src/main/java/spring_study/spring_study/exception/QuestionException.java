package spring_study.spring_study.exception;

/**
 * 모든 질문 페이지 예외 관련 질문 예외의 부모 예외이다.
 */
public class QuestionException extends RuntimeException{
    public QuestionException(String message){
        super(message);
    }
}
