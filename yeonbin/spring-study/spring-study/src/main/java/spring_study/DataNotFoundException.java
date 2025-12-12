package spring_study;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//이 예외가 발생하면 HTTP 상태 코드 404 (Not Found)를 반환하라는 의미
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "entity not found")
//RuntimeException을 상속 받은 예외들은 검사 예외 처리 강제X
public class DataNotFoundException extends RuntimeException {
    private static final long serialVersionID = 1L;
    public DataNotFoundException(String message) {
        super(message);
    }
}
