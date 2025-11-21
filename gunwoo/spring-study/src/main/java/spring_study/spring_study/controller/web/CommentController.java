package spring_study.spring_study.controller.web;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import spring_study.spring_study.domain.Comment;
import spring_study.spring_study.dto.crud.request.CommentCreateRequest;
import spring_study.spring_study.service.CommentService;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board/{postId}/comments")
public class CommentController {

    private final CommentService commentService;

    // 댓글 조회
    @GetMapping
    public String comments(@PathVariable long postId, Model model) {
        List<Comment> comments = commentService.getAllCommentsByPostId(postId);
        model.addAttribute("comments", comments);
        return "fragments/comment-list :: comments-list";
    }

    // 댓글 작성
    @PostMapping
    public String createComment(
            @PathVariable Long postId,
            @Valid @ModelAttribute CommentCreateRequest request,
            Model model
    ) {

        // BindException 발생 시 GlobalExceptionHandler가 처리
        Long commentId = commentService.createComment(
                postId,
                request.userId(),
                request.content()
                );
        Comment comment = commentService.getCommentByCommentId(commentId);
        model.addAttribute("comment", comment);

        return "fragments/comment-detail :: commentDetail";
    }

    // 댓글 수정
    @PutMapping("/{commentId}")
    public String updateComment(
            @PathVariable Long postId,
            @PathVariable Long commentId,
            @RequestParam String content,
            @RequestParam Long userId,
            Model model
    ) {
        commentService.updateComment(commentId, userId, content);
        Comment comment = commentService.getCommentByCommentId(commentId);
        model.addAttribute("comment", comment);
        return "fragments/comment-detail :: commentDetail";
    }

    // 댓글 삭제는 JSON응답 (삭제시 HTML 필요없음)
    @DeleteMapping("/{commentId}")
    @ResponseBody   // JSON응답 시 필요 // 상태코드 자유자재로 제어하기위해 void 대신 ResponseEntity<Void> 반환
    public ResponseEntity<Void> deleteComment(
            @PathVariable Long postId,
            @PathVariable Long commentId,
            @RequestParam Long userId
    ) {
    commentService.deleteComment(commentId, userId);
        return ResponseEntity.noContent().build();
    }
}
