package spring_study.spring_study.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import spring_study.spring_study.exception.CommentNotFoundException;
import spring_study.spring_study.repository.CommentRepository;
import spring_study.spring_study.repository.PostRepository;
import spring_study.spring_study.repository.UserRepository;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class) // Mockito 사용 선언
class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private CommentService commentService;

    @Test
    @DisplayName("단일 댓글조회시 해당하는 댓글이 없다")
    public void commentNotFound() {

        // given
        Long commentId = 999L;

        given(commentRepository.findById(commentId))
                .willReturn(Optional.empty());

        // when
        assertThatThrownBy(() -> commentService.getCommentByCommentId(commentId))
                .isInstanceOf(CommentNotFoundException.class);
        // then
        then(commentRepository).should().findById(commentId);
    }

}