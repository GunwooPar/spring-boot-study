package spring_study.spring_study.dto;

import jakarta.validation.constraints.NotBlank;

public record MemberEditRequest(
        @NotBlank
        String userName,
        @NotBlank
        String userAge)
{ }
