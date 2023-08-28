package com.prograngers.backend.service;

import com.prograngers.backend.dto.comment.request.CommentPatchRequest;
import com.prograngers.backend.entity.comment.Comment;
import com.prograngers.backend.entity.member.Member;
import com.prograngers.backend.entity.problem.Problem;
import com.prograngers.backend.entity.solution.Solution;
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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.prograngers.backend.entity.comment.CommentStatusConStant.DELETED;
import static com.prograngers.backend.entity.solution.AlgorithmConstant.BFS;
import static com.prograngers.backend.entity.solution.DataStructureConstant.LIST;
import static com.prograngers.backend.entity.solution.LanguageConstant.JAVA;
import static com.prograngers.backend.fixture.CommentFixture.삭제된_댓글;
import static com.prograngers.backend.fixture.CommentFixture.생성된_댓글;
import static com.prograngers.backend.fixture.MemberFixture.장지담;
import static com.prograngers.backend.fixture.ProblemFixture.백준_문제;
import static com.prograngers.backend.fixture.SolutionFixture.퍼블릭_풀이;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
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
        Member member = 저장(장지담.기본_정보_생성());
        Problem problem = 백준_문제.기본_정보_생성();
        Solution solution = 저장(퍼블릭_풀이.기본_정보_생성(problem,member, LocalDateTime.now(),BFS, LIST,JAVA,1));


        Comment comment1 = 생성된_댓글.기본_정보_생성(member,solution,LocalDateTime.now());
        Comment comment2 = 생성된_댓글.기본_정보_생성(member,solution,LocalDateTime.now());

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
        Member member = 저장(장지담.기본_정보_생성());
        Problem problem = 백준_문제.기본_정보_생성();
        Solution solution = 저장(퍼블릭_풀이.기본_정보_생성(problem,member, LocalDateTime.now(),BFS, LIST,JAVA,1));


        Comment comment1 = 생성된_댓글.기본_정보_생성(member,solution,LocalDateTime.now());
        Comment comment2 = 생성된_댓글.기본_정보_생성(member,solution,LocalDateTime.now());

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
        Member member = 장지담.아이디_지정_생성(1L);
        Problem problem = 백준_문제.기본_정보_생성();
        Solution solution = 퍼블릭_풀이.기본_정보_생성(problem,member, LocalDateTime.now(),BFS, LIST,JAVA,1);
        Comment comment = 생성된_댓글.기본_정보_생성(member,solution,LocalDateTime.now());


        given(commentRepository.save(comment)).willReturn(comment);
        given(commentRepository.findById(comment.getId())).willReturn(Optional.ofNullable(comment));
        given(memberRepository.findById(member.getId())).willReturn(Optional.ofNullable(member));
        CommentPatchRequest request = new CommentPatchRequest("수정내용", null);

        // when
        commentService.updateComment(comment.getId(), request, member.getId());
        Comment updated = commentRepository.findById(comment.getId()).orElse(null);

        // then
        Assertions.assertThat(updated.getContent()).isEqualTo("수정내용");

    }

    @DisplayName("댓글을 삭제할 수 있다")
    @Test
    void 댓글_삭제_테스트() {
        // given
        Member member = 장지담.아이디_지정_생성(1L);
        Problem problem = 백준_문제.기본_정보_생성();
        Solution solution = 퍼블릭_풀이.기본_정보_생성(problem,member, LocalDateTime.now(),BFS, LIST,JAVA,1);
        Comment comment = 생성된_댓글.기본_정보_생성(member,solution,LocalDateTime.now());
        Comment deleted = 삭제된_댓글.기본_정보_생성(member,solution,LocalDateTime.now());


        given(commentRepository.save(comment))
                .willReturn(comment)
                        .willReturn(deleted);
        given(commentRepository.findById(comment.getId())).
                willReturn(Optional.ofNullable(comment))
                        .willReturn(Optional.ofNullable(deleted));
        given(memberRepository.findById(member.getId()))
                .willReturn(Optional.ofNullable(member));

        commentRepository.save(comment);


        // when
        commentService.deleteComment(comment.getId(),member.getId());

        // then
        verify(commentRepository,times(2)).save(comment);
        Comment found = commentRepository.findById(deleted.getId()).orElse(null);
        Assertions.assertThat(found.getStatus()).isEqualTo(DELETED);

    }

    @DisplayName("없는 댓글을 조회할 경우 예외 발생")
    @Test
    void 없는_댓글_조회() {
        org.junit.jupiter.api.Assertions.assertThrows(CommentNotFoundException.class, () -> commentService.findById(1L));
    }

    Member 저장(Member member) {
        return memberRepository.save(member);
    }

    Solution 저장(Solution solution) {
        return solutionRepository.save(solution);
    }
}