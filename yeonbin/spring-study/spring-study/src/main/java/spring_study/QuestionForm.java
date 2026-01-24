package spring_study;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
//입력값 검증
public class QuestionForm {
    @NotEmpty(message = "제목은 필수 항목입니다.")
    @Size(max = 20)
    private String subject;

    @NotEmpty(message = "내용은 필수 항목입니다.")
    private String content;
}
