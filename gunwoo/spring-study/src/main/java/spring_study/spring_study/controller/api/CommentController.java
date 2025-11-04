package spring_study.spring_study.controller.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring_study.spring_study.service.CommentService;

@RestController
@RequiredArgsConstructor
@RequestMapping(("/board/{postId}/comments"))
public class CommentController {

    private final CommentService commentService;

    @DeleteMapping
    public ResponseEntity<Void> deleteComment(
            @PathVariable Long postId,
            @PathVariable Long commentId,
            @RequestParam Long userId
    ) {
        commentService.deleteComment(postId, commentId, userId);
        return ResponseEntity.notFound().build();
    }

}
