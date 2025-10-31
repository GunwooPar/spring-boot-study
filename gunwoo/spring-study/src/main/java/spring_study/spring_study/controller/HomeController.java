package spring_study.spring_study.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import spring_study.spring_study.domain.Post;
import spring_study.spring_study.service.PostService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final PostService postService;

    @GetMapping("/")
    public String home() {
        return "home";
    }

    // 게시글 목록
    @GetMapping("/board")
    public String board(Model model) {
        List<Post> posts = postService.getAllPosts();
        model.addAttribute("posts", posts);
        return "board";
    }

    // 게시글 작성 폼 (구체적인 경로를 먼저 매핑)
    @GetMapping("/board/new")
    public String boardCreateForm() {
        return "board-create";
    }

    // 게시글 상세
    @GetMapping("/board/{id}")
    public String boardDetail(@PathVariable Long id, Model model) {
        Post post = postService.getPost(id);
        model.addAttribute("post", post);
        return "board-detail";
    }

    // 게시글 작성 처리
    @PostMapping("/board")
    public String boardCreate(@RequestParam String title,
                             @RequestParam String content,
                             @RequestParam Long userId) {
        Long postId = postService.createPost(title, content, userId);
        return "redirect:/board/" + postId; // 중복 제출 방지
    }

    // 게시글 수정 폼
    @GetMapping("/board/{id}/edit")
    public String boardEditForm(@PathVariable Long id, Model model) {
        Post post = postService.getPost(id);
        model.addAttribute("post", post);
        return "board-edit";
    }

    // 게시글 수정 처리
    @PostMapping("/board/{id}/edit")
    public String boardUpdate(@PathVariable Long id,
                             @RequestParam String title,
                             @RequestParam String content) {
        postService.updatePost(id, title, content);
        return "redirect:/board/" + id;
    }

    // 게시글 삭제 처리
    @PostMapping("/board/{id}/delete")
    public String boardDelete(@PathVariable Long id) {
        postService.deletePost(id);
        return "redirect:/board";
    }
}