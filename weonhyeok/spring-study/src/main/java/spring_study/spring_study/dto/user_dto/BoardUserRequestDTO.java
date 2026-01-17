package spring_study.spring_study.dto.user_dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record BoardUserRequestDTO(
        @Size(min=3, max = 30)
        @NotBlank(message = "사용자 ID는 필수 항목입니다.")
        String userId,

        @NotBlank(message = "비밀번호는 필수 항목 입니다.")
        String userPassword,

        @NotBlank(message = "비밀번호 확인은 필수 항목입니다.")
        String userPasswordCheck,

        @Email
        @NotBlank(message = "이메일은 필수 항목입니다.")
        String userEmail
) { }
