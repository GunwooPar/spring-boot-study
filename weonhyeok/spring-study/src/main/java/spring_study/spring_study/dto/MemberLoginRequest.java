package spring_study.spring_study.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * 해당 record class는 로그인 서비스 로직 수행 시 사용할 DTO 클래스입니다.
 *
 * @param userName 로그인 필수 정보인 사용자 이름
 * @param password 로그인 필수 정보인 사용자 비밀번호
 */
public record MemberLoginRequest(
        @NotBlank(message = "사용자 이름의 정보는 반드시 존재해야함")
        String userName,
        @NotBlank(message = "사용자 비밀번호는 반드시 존재해야함")
        String password
) {
}
