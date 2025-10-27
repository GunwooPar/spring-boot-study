package spring_study.spring_study.dto.crud.response;

import spring_study.spring_study.domain.Comment;

import java.time.Instant;

public record CommentResponse(
        Long id,
        String content,
        UserResponse userResponse,
        Instant createdAt
) {
    public static CommentResponse from(Comment comment) {
        return new CommentResponse(
                comment.getId(),
                comment.getContent(),
                UserResponse.from(comment.getUser()),
                comment.getCreatedAt()
        );
    }
}
