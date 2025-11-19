package spring_study.spring_study.exception.answer_exception;

public class AnswerContentBlankException extends AnswerException {
    public AnswerContentBlankException(AnswerExceptionMessageEnum error) {
        super(error.getErrorMessage(), error.getHttpStatus());
    }
}
