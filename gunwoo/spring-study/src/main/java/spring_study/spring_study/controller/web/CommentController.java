package spring_study.spring_study.controller.web;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import spring_study.spring_study.service.CommentService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board/{postId}/comments")
public class CommentController {

    private final CommentService commentService;

    // 댓글 조회

    // 댓글 작성
    @PostMapping
    public String createComment(
            @PathVariable Long postId,
            @RequestParam Long userId,
            @RequestParam String content
    ) {

        commentService.createComment(postId, userId, content);
        return "redirect:/board/" + postId;
    }

    // 댓글 수정

    // 댓글 삭제
    @PostMapping("/{commentId}/delete")
    public String deleteComment(
            @PathVariable Long postId,
            @PathVariable Long commentId,
            @RequestParam Long userId
    ) {
        commentService.deleteComment(postId, userId, commentId);
        return "redirect:/board/" + postId;
    }
}
