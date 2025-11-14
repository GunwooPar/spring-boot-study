package spring_study.spring_study.dto;

import java.time.LocalDate;

public record AnswerRequest(
    Long id,
    String content,
    LocalDate createDate
) {}
