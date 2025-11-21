package spring_study.spring_study.dto.crud.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CommentUpdateRequest(

    Long postId,

    String content

) {

}
