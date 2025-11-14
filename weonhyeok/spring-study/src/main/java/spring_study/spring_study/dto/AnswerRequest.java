package spring_study.spring_study.dto;

import java.time.LocalDateTime;

public record AnswerRequest(
    Long id,
    String content,
    LocalDateTime createDate
) {}
