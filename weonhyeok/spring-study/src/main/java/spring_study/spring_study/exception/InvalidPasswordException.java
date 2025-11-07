package spring_study.spring_study.exception;

/**
 * 비밀번호 불일치 예외
 */
public class InvalidPasswordException extends MemberException{
    public InvalidPasswordException(){
        super("[ERROR] 비밀번호 불일치");
    }

    public InvalidPasswordException(String message){
        super(message);
    }
}
