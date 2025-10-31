package spring_study.spring_study.dto.crud.response;

import spring_study.spring_study.domain.Post;

import java.time.Instant;
import java.util.List;

public record PostDetailResponse(
        Long id,
        String title,
        String content,
        UserResponse userResponse,
        Post.Status status,
        Instant createdAt,
        List<CommentResponse> comments
) {
    public static PostDetailResponse from(Post post, List<CommentResponse> comments) {
        return new PostDetailResponse(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                UserResponse.from(post.getUser()),
                post.getStatus(),
                post.getCreatedAt(),
                comments
        );
    }
}
