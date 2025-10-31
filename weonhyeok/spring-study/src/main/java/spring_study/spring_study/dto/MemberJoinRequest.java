package spring_study.spring_study.dto;

import jakarta.validation.constraints.NotBlank;

public record MemberJoinRequest(
        @NotBlank(message = "사용자이름은 필수입니다.")
        String userName,
        @NotBlank(message = "사용자 나이는 0보다 커야합니다.")
        String userAge) {}