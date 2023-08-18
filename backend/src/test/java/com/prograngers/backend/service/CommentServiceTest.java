package com.prograngers.backend.service;

import com.prograngers.backend.dto.comment.request.CommentPatchRequest;
import com.prograngers.backend.entity.Comment;
import com.prograngers.backend.entity.member.Member;
import com.prograngers.backend.entity.Solution;
import com.prograngers.backend.exception.notfound.CommentNotFoundException;
import com.prograngers.backend.repository.comment.CommentRepository;
import com.prograngers.backend.repository.member.MemberRepository;
import com.prograngers.backend.repository.solution.SolutionRepository;
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

import static com.prograngers.backend.fixture.CommentFixture.댓글1;
import static com.prograngers.backend.fixture.CommentFixture.댓글2;
import static com.prograngers.backend.fixture.CommentFixture.삭제된_댓글;
import static com.prograngers.backend.fixture.MemberFixture.*;
import static com.prograngers.backend.fixture.SolutionFixture.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
@Slf4j
class CommentServiceTest {
    @Mock
    private CommentRepository commentRepository;
    @Mock
    private SolutionRepository solutionRepository;

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private CommentService commentService;

    @DisplayName("풀이로 댓글을 찾을 수 있다")
    @Test
    void 솔루션으로_댓글_찾기_테스트() {

        // given
        Solution solution = 풀이_저장(풀이1.기본_솔루션_생성(1L));
        Member member = 멤버_저장(길가은1.getMember());
        Comment comment1 = 댓글1.댓글_생성(1L, solution, member);
        Comment comment2 = 댓글1.댓글_생성(2L, solution, member);

        List<Comment> comments = new ArrayList<>();
        comments.add(comment1);
        comments.add(comment2);

        when(commentRepository.findAllBySolution(any())).thenReturn(comments);

        // when
        List<Comment> bySolution = commentService.findBySolution(solution);

        // then
        Assertions.assertThat(bySolution.size()).isEqualTo(2);
    }

    @DisplayName("댓글 아이디로 댓글을 찾을 수 있다")
    @Test
    void 아이디로_댓글_찾기_테스트() {
        // given
        Comment comment1 = 댓글1.기본_댓글_생성(1L);
        Comment comment2 = 댓글2.기본_댓글_생성(2L);

        when(commentRepository.save(any())).thenReturn(comment1).thenReturn(comment2);
        when(commentRepository.findById(1L)).thenReturn(Optional.ofNullable(comment1));

        Comment saved = commentRepository.save(comment1);
        commentRepository.save(comment2);

        // when
        Comment found = commentService.findById(1L);

        // then
        Assertions.assertThat(found).isEqualTo(saved);
    }

    @DisplayName("댓글을 수정할 수 있다")
    @Test
    void 댓글_수정_테스트() {

        // given
        Solution solution = 풀이1.기본_솔루션_생성(1L);
        Member member = 길가은1.getMember();
        Comment comment = 댓글1.댓글_생성(1L, solution, member);

        given(commentRepository.save(comment)).willReturn(comment);
        given(commentRepository.findById(comment.getId())).willReturn(Optional.ofNullable(comment));
        CommentPatchRequest request = new CommentPatchRequest("수정내용", null);

        // when
        commentService.updateComment(comment.getId(), request);
        Comment updated = commentRepository.findById(1L).orElse(null);

        // then
        Assertions.assertThat(updated.getContent()).isEqualTo("수정내용");

    }

    @DisplayName("댓글을 삭제할 수 있다")
    @Test
    void 댓글_삭제_테스트() {
        // given
        Comment comment = 댓글1.기본_댓글_생성(1L);
        Comment deleted = 삭제된_댓글.기본_댓글_생성(1L);

        given(commentRepository.save(comment))
                .willReturn(comment)
                        .willReturn(deleted);
        given(commentRepository.findById(comment.getId())).
                willReturn(Optional.ofNullable(comment))
                        .willReturn(Optional.ofNullable(deleted));

        commentRepository.save(comment);

        // when
        commentService.deleteComment(1L);

        // then
        verify(commentRepository,times(2)).save(comment);
        Comment found = commentRepository.findById(1L).orElse(null);
        Assertions.assertThat(found.getContent()).isEqualTo("삭제된 댓글입니다");

    }

    @DisplayName("없는 댓글을 조회할 경우 예외 발생")
    @Test
    void 없는_댓글_조회() {
        org.junit.jupiter.api.Assertions.assertThrows(CommentNotFoundException.class, () -> commentService.findById(1L));
    }

    Member 멤버_저장(Member member) {
        return memberRepository.save(member);
    }

    Solution 풀이_저장(Solution solution) {
        return solutionRepository.save(solution);
    }
}