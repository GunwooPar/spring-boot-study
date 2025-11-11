package spring_study.spring_study.dto.crud.request;

public record CommentUpdateRequest(
    Long postId,
    String content

) {

}
