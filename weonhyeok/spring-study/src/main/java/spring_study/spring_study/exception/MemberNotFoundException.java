package spring_study.spring_study.exception;

/**
 * 사용자 존재하지 않는 사용자 예외
 */
public class MemberNotFoundException extends MemberException {
    public MemberNotFoundException() {
        super("[ERROR] 사용자를 찾을 수 없습니다.");
    }

    public MemberNotFoundException(String message) {
        super(message);
    }
}
