package com.prograngers.backend.service;

import com.prograngers.backend.entity.Comment;
import com.prograngers.backend.entity.Solution;
import com.prograngers.backend.repository.CommentRepository;
import com.prograngers.backend.repository.SolutionRepository;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;


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

    @Test
    void 솔루션으로_댓글_찾기_테스트(){

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

    @Test
    void 아이디로_댓글_찾기_테스트(){
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

    @Test
    void 댓글_수정_테스트(){
        // given
        Comment comment1 = Comment.builder()
                .content("내용1")
                .build();

        given(commentRepository.save(comment1)).willReturn(comment1);

        // when
        Comment saved = commentRepository.save(comment1);
        saved.updateContent("수정내용");
        Comment updated = commentRepository.save(saved);

        Assertions.assertThat(updated).isEqualTo(saved);
    }


}