package spring_study.spring_study.exception;

/**
 * 중북검사 사용자 예외
 */
public class DuplicateMemberException extends MemberException {
    public DuplicateMemberException() {
        super("[EXCEPTION] 이미 존재하는 회원입니다.");
    }

    public DuplicateMemberException(String message) {
        super(message);
    }
}
