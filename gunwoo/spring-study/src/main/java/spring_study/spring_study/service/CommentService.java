package spring_study.spring_study.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring_study.spring_study.domain.Comment;
import spring_study.spring_study.domain.Post;
import spring_study.spring_study.domain.User;
import spring_study.spring_study.exception.CommentNotFoundException;
import spring_study.spring_study.repository.CommentRepository;
import spring_study.spring_study.repository.PostRepository;
import spring_study.spring_study.repository.UserRepository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    // 모든 댓글 조회
    public List<Comment> getAllCommentsByPostId(Long postId) {
        return commentRepository.findByPostId(postId);
    }

    // 단일 댓글 조회 
    public Comment getCommentByCommentId(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(()-> new CommentNotFoundException());
    }

    // 댓글 생성
    @Transactional
    public Long createComment(Long postId,Long userId, String content) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다: "+ userId));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시판을 찾을 수 없습니다: "+ postId));

        Comment comment = Comment.builder()
                .content(content)
                .post(post)
                .user(user)
                .createdAt(Instant.now())
                .status(Comment.Status.PUBLISHED)
                .build();

        Comment savedComment = commentRepository.save(comment);

        return savedComment.getId();
    }



    // 댓글 수정
    @Transactional
    public void updateComment(Long commentId, Long userId, String content) {
        
        
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("댓글을 찾을 수 없습니다: " + commentId));
        
        if (!comment.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("댓글 수정 권한이 없습니다");
        }

        comment.updateContent(content);

        // @Transactional 메서드 종료시점에 스냅샷과 현재 엔티티를 비교, 더티 체킹후 자동으로 UPDATE 쿼리 생성 및 실행
    }

    // 댓글 삭제
    @Transactional
    public void deleteComment(Long commentId, Long userId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(()-> new IllegalArgumentException("댓글을 찾을 수 없습니다: " + commentId));

        if (!comment.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("댓글 삭제 권한이 없습니다");
        }



        comment.delete();

    }

}
