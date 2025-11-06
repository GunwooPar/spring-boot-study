package spring_study.spring_study.exception;

import spring_study.spring_study.domain.Member;

/**
 * 나이 음수에 대한 사용자 예외
 */
public class InvalidAgeException extends MemberException {
    public InvalidAgeException() {
        super("[ERROR] 나이는 0보다 커야합니다.");
    }

    public InvalidAgeException(String message) {
        super(message);
    }
}
