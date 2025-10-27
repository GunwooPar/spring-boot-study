package spring_study.spring_study.dto.crud.response;

import spring_study.spring_study.domain.Post;

import java.time.Instant;

public record PostResponse(
        Long id,
        String title,
        UserResponse userResponse,
        Post.Status status,
        Instant createdAt
) {
    public static PostResponse from(Post post) {
        return new PostResponse(
                post.getId(),
                post.getTitle(),
                UserResponse.from(post.getUser()),
                post.getStatus(),
                post.getCreatedAt()
        );
    }

}
