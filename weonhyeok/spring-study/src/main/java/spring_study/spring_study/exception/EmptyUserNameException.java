package spring_study.spring_study.exception;

public class EmptyUserNameException extends MemberException {
    public EmptyUserNameException(){
        super("[ERROR] 입력된 사용자 이름이 비어있습니다.");
    }

    public EmptyUserNameException(String message) {
        super(message);
    }
}
