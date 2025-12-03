package spring_study.spring_study.controller.web;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import spring_study.spring_study.domain.Comment;
import spring_study.spring_study.domain.Post;
import spring_study.spring_study.security.CustomUserDetails;
import spring_study.spring_study.service.CommentService;
import spring_study.spring_study.service.PostService;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class PostController {

    private final PostService postService;
    private final CommentService commentService;

    // 게시글 목록
    @GetMapping
    public String board(Model model) {
        List<Post> posts = postService.getAllPosts();
        model.addAttribute("posts", posts);
        return "board";
    }

    // 게시글 작성 폼 (구체적인 경로를 먼저 매핑)
    @GetMapping("/new")
    public String boardCreateForm() {
        return "board-create";
    }

    // 게시글 상세(댓글 목록도 함께 조회)
    @GetMapping("/{id}")
    public String boardDetail(@PathVariable Long id,
                              @AuthenticationPrincipal CustomUserDetails userDetails,
                              Model model) {
        Post post = postService.getPost(id);
        List<Comment> comments = commentService.getAllCommentsByPostId(id);

        model.addAttribute("post", post);
        model.addAttribute("comments", comments);
        model.addAttribute("currentUser", userDetails.getUser());
        return "board-detail";
    }

    // 게시글 작성 처리
    @PostMapping
    public String boardCreate(@RequestParam String title,
                              @RequestParam String content,
                              @AuthenticationPrincipal CustomUserDetails userDetails) {
        Long userId = userDetails.getUser().getId();
        Long postId = postService.createPost(title, content, userId);
        return "redirect:/board/" + postId; // 중복 제출 방지
    }

    // 게시글 수정 폼
    @GetMapping("/{id}/edit")
    public String boardEditForm(@PathVariable Long id, Model model) {
        Post post = postService.getPost(id);
        model.addAttribute("post", post);
        return "board-edit";
    }

    // 게시글 수정 처리
    @PutMapping("/{id}/edit")
    public String boardUpdate(@PathVariable Long id,
                              @RequestParam String title,
                              @RequestParam String content,
                              @AuthenticationPrincipal CustomUserDetails userDetails) {
        Long userId = userDetails.getUser().getId();
        postService.updatePost(id, title, content, userId);
        return "redirect:/board/" + id;
    }

    // 게시글 삭제 처리
    @DeleteMapping("/{id}/delete")
    public String boardDelete(@PathVariable Long id,
                              @AuthenticationPrincipal CustomUserDetails userDetails) {
        Long userId = userDetails.getUser().getId();
        postService.deletePost(id, userId);
        return "redirect:/board";
    }
}
