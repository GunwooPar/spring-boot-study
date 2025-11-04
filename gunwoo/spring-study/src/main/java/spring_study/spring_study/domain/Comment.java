package spring_study.spring_study.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(exclude = {"post", "user"}) // ToString은 연관관계 필드 반드시 제거해야함
@Entity
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    @ManyToOne
    @JoinColumn(name="post_id")
    private Post post;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    private Instant createdAt;

    @Enumerated(EnumType.STRING)
    private Status status;

    public enum Status {
        PUBLISHED,
        DELETED
    }




    public void updateContent(String content) {
        this.content = content;
    }

    public void updateContent(String content, Instant createdAt) {
        this.content = content;
    }

    public void updateCreatedAt() {
        this.createdAt = Instant.now();
    }

    public void delete() {
        this.status = Status.DELETED;
    }
}
