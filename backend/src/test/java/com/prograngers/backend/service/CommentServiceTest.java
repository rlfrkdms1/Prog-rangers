package com.prograngers.backend.service;

import com.prograngers.backend.dto.CommentPatchRequest;
import com.prograngers.backend.entity.Comment;
import com.prograngers.backend.entity.Solution;
import com.prograngers.backend.exception.notfound.CommentNotFoundException;
import com.prograngers.backend.repository.CommentRepository;
import com.prograngers.backend.repository.SolutionRepository;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
@Slf4j
class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    SolutionRepository solutionRepository;
    @InjectMocks
    private CommentService commentService;

    @InjectMocks
    private SolutionService solutionService;

    @DisplayName("풀이로 댓글을 찾을 수 있다")
    @Test
    void 솔루션으로_댓글_찾기_테스트() {

        // given
        Solution solution = Solution.builder()
                .description("설명").build();
        Comment comment1 = Comment.builder()
                .content("내용1")
                .solution(solution).build();
        Comment comment2 = Comment.builder()
                .content("내용2")
                .solution(solution).build();

        List<Comment> comments = new ArrayList<>();
        comments.add(comment1);
        comments.add(comment2);

        given(commentRepository.findAllBySolution(solution)).willReturn(comments);

        // when
        List<Comment> bySolution = commentService.findBySolution(solution);

        // then
        Assertions.assertThat(bySolution.size()).isEqualTo(2);
    }

    @DisplayName("댓글 아이디로 댓글을 찾을 수 있다")
    @Test
    void 아이디로_댓글_찾기_테스트() {
        // given
        Comment comment1 = Comment.builder()
                .content("내용1")
                .build();
        Comment comment2 = Comment.builder()
                .content("내용2")
                .build();

        given(commentRepository.save(comment1)).willReturn(comment1);
        given(commentRepository.findById(1L)).willReturn(Optional.ofNullable(comment1));

        // when
        Comment saved = commentRepository.save(comment1);
        commentRepository.save(comment2);
        Comment found = commentService.findById(1L);

        // then
        Assertions.assertThat(found).isEqualTo(saved);
    }

    @DisplayName("댓글을 수정할 수 있다")
    @Test
    void 댓글_수정_테스트() {

        // given
        Solution solution = Solution.builder()
                .id(null)
                .description("풀이설명")
                .build();

        Comment comment = Comment.builder()
                .id(1L)
                .solution(solution)
                .content("댓글내용")
                .build();
        given(commentRepository.save(comment)).willReturn(comment);
        given(commentRepository.findById(comment.getId())).willReturn(Optional.ofNullable(comment));
        CommentPatchRequest request = new CommentPatchRequest("수정내용",null);

        // when
        commentService.updateComment(comment.getId(),request);
        Comment updated = commentRepository.findById(1L).orElse(null);

        // then
        Assertions.assertThat(updated.getContent()).isEqualTo("수정내용");

    }

    @DisplayName("댓글을 삭제할 수 있다")
    @Test
    void 댓글_삭제_테스트(){
        // given
        Comment comment = Comment.builder()
                .id(1L)
                .content("댓글내용")
                .build();

        given(commentRepository.save(comment)).willReturn(comment);
        given(commentRepository.findById(comment.getId())).willReturn(Optional.ofNullable(comment));
        commentRepository.save(comment);

        // when
        commentService.deleteComment(1L);

        // then
        verify(commentRepository).delete(comment);
    }

    @DisplayName("없는 댓글을 조회할 경우 예외 발생")
    @Test
    void 없는_댓글_조회(){
        org.junit.jupiter.api.Assertions.assertThrows(
                CommentNotFoundException.class,
                ()->commentService.findById(1L)
        );
    }

}