package com.prograngers.backend.service;

import static com.prograngers.backend.entity.solution.AlgorithmConstant.BFS;
import static com.prograngers.backend.entity.solution.DataStructureConstant.LIST;
import static com.prograngers.backend.entity.solution.LanguageConstant.JAVA;
import static com.prograngers.backend.support.fixture.CommentFixture.생성된_댓글;
import static com.prograngers.backend.support.fixture.MemberFixture.장지담;
import static com.prograngers.backend.support.fixture.ProblemFixture.백준_문제;
import static com.prograngers.backend.support.fixture.SolutionFixture.공개_풀이;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

import com.prograngers.backend.dto.comment.request.UpdateCommentRequest;
import com.prograngers.backend.entity.comment.Comment;
import com.prograngers.backend.entity.member.Member;
import com.prograngers.backend.entity.problem.Problem;
import com.prograngers.backend.entity.solution.Solution;
import com.prograngers.backend.exception.UnAuthorizationException;
import com.prograngers.backend.repository.comment.CommentRepository;
import com.prograngers.backend.repository.member.MemberRepository;
import com.prograngers.backend.repository.solution.SolutionRepository;
import java.time.LocalDateTime;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


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


    @DisplayName("댓글을 수정할 수 있다")
    @Test
    void 댓글_수정_테스트() {

        // given
        Member member = 장지담.아이디_지정_생성(1L);
        Problem problem = 백준_문제.기본_정보_생성();
        Solution solution = 공개_풀이.태그_추가_생성(problem, member, LocalDateTime.now(), BFS, LIST, JAVA, 1);
        Comment comment = 생성된_댓글.기본_정보_생성(member, solution, LocalDateTime.now());

        given(commentRepository.save(comment)).willReturn(comment);
        given(commentRepository.findById(comment.getId())).willReturn(Optional.ofNullable(comment));
        given(memberRepository.findById(member.getId())).willReturn(Optional.ofNullable(member));
        UpdateCommentRequest request = new UpdateCommentRequest("수정내용");

        // when
        commentService.updateComment(comment.getId(), request, member.getId());
        Comment updated = commentRepository.findById(comment.getId()).orElse(null);

        // then
        assertThat(updated.getContent()).isEqualTo("수정내용");

    }

    @DisplayName("내 댓글이 아닌 댓글을  수정하려 할 경우 예외 발생")
    @Test
    void 내_댓글_아닌_댓글_수정() {
        // given
        Member member1 = 장지담.아이디_지정_생성(1L);
        Member member2 = 장지담.아이디_지정_생성(2L);
        Problem problem = 백준_문제.기본_정보_생성();
        Solution solution = 공개_풀이.아이디_지정_생성(1L, problem, member1, LocalDateTime.now(), BFS, LIST, JAVA, 1);
        Comment comment = 생성된_댓글.아이디_지정_생성(1L, member1, solution, LocalDateTime.now());

        UpdateCommentRequest request = 댓글_수정_요청_생성("수정 댓글", "수정 멘션");

        when(commentRepository.findById(any())).thenReturn(Optional.of(comment));
        when(memberRepository.findById(any())).thenReturn(Optional.of(member2));

        // when then
        // member1의 댓글을 member2가 수정하려 한다
        Assertions.assertThrows(
                UnAuthorizationException.class,
                () -> commentService.updateComment(comment.getId(), request, member2.getId())
        );
    }

    @DisplayName("내 댓글이 아닌 댓글을  삭제하려 할 경우 예외 발생")
    @Test
    void 내_댓글_아닌_댓글_삭제() {
        // given
        Member member1 = 장지담.아이디_지정_생성(1L);
        Member member2 = 장지담.아이디_지정_생성(2L);
        Problem problem = 백준_문제.기본_정보_생성();
        Solution solution = 공개_풀이.아이디_지정_생성(1L, problem, member1, LocalDateTime.now(), BFS, LIST, JAVA, 1);
        Comment comment = 생성된_댓글.아이디_지정_생성(1L, member1, solution, LocalDateTime.now());

        UpdateCommentRequest request = 댓글_수정_요청_생성("수정 댓글", "수정 멘션");

        when(commentRepository.findById(any())).thenReturn(Optional.of(comment));
        when(memberRepository.findById(any())).thenReturn(Optional.of(member2));

        // when then
        // member1의 댓글을 member2가 삭제하려 한다
        Assertions.assertThrows(
                UnAuthorizationException.class,
                () -> commentService.deleteComment(comment.getId(), member2.getId())
        );
    }

    Member 저장(Member member) {
        return memberRepository.save(member);
    }

    Solution 저장(Solution solution) {
        return solutionRepository.save(solution);
    }

    UpdateCommentRequest 댓글_수정_요청_생성(String content, String mention) {
        return new UpdateCommentRequest(content);
    }
}