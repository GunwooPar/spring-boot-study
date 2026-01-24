package spring_study.spring_study.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import spring_study.spring_study.domain.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByUserId(Long userId);

    // List<Comment> findByPostId(Long postId); 기존 (N+1문제 발생)


    // N+1 문제 해결 위해 Join Fetch 사용
    @Query("SELECT c FROM Comment c " +
            "JOIN FETCH c.user " +
            "WHERE c.post.id = :postId " +
            "AND c.status != 'DELETED'")
    List<Comment> findByPostIdWithUser(@Param("postId") Long postId);

}
