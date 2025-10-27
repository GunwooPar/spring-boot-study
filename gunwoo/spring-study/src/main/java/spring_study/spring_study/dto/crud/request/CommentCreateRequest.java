package spring_study.spring_study.dto.crud.request;

public record CommentCreateRequest(
    Long postId,
    String content
) {
}
