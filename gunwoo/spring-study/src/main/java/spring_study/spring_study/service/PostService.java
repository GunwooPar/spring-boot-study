package spring_study.spring_study.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring_study.spring_study.domain.Post;
import spring_study.spring_study.domain.User;
import spring_study.spring_study.repository.PostRepository;
import spring_study.spring_study.repository.UserRepository;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    // 전체 게시글 조회
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    // 게시글 상세 조회
    public Post getPost(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다: " + postId));
    }

    // 게시글 생성
    @Transactional
    public Long createPost(String title, String content, Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다: " + userId));

        Post post = Post.builder()
            .title(title)
            .content(content)
            .user(user)
            .status(Post.Status.PUBLISHED)
            .createdAt(Instant.now())
            .build();

        Post savedPost = postRepository.save(post);
        return savedPost.getId();
    }

    // 게시글 수정
    @Transactional
    public void updatePost(Long postId, String title, String content) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다: " + postId));

        post.updateTitle(title);
        post.updateContent(content);
    }



    // 게시글 삭제
    @Transactional
    public void deletePost(Long postId,  Long userId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다: " + postId));

        if (!post.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("게시글 삭제 권한이 없습니다");

        }

        post.delete();
    }
}
