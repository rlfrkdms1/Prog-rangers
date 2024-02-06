package com.prograngers.backend.repository.comment;

import static com.prograngers.backend.entity.solution.LanguageConstant.JAVA;
import static com.prograngers.backend.support.fixture.CommentFixture.생성된_댓글;
import static com.prograngers.backend.support.fixture.CommentFixture.수정된_댓글;
import static com.prograngers.backend.support.fixture.MemberFixture.길가은;
import static com.prograngers.backend.support.fixture.MemberFixture.장지담;
import static com.prograngers.backend.support.fixture.ProblemFixture.백준_문제;
import static com.prograngers.backend.support.fixture.SolutionFixture.공개_풀이;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.prograngers.backend.entity.comment.Comment;
import com.prograngers.backend.entity.member.Member;
import com.prograngers.backend.entity.problem.Problem;
import com.prograngers.backend.entity.solution.Solution;
import com.prograngers.backend.repository.member.MemberRepository;
import com.prograngers.backend.repository.problem.ProblemRepository;
import com.prograngers.backend.repository.solution.SolutionRepository;
import com.prograngers.backend.support.RepositoryTest;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;

@RepositoryTest
class CommentRepositoryTest {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private SolutionRepository solutionRepository;
    @Autowired
    private ProblemRepository problemRepository;
    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("주어진 회원이 삭제한 댓글 외 작성한 댓글을 페이징해 조회할 수 있다.")
    void 내_댓글_조회() {
        Member member1 = 저장(길가은.기본_정보_생성());
        Member member2 = 저장(장지담.기본_정보_생성());
        Problem problem = 저장(백준_문제.기본_정보_생성());
        Solution solution = 저장(공개_풀이.기본_정보_생성(problem, member1, LocalDateTime.now(), JAVA, 1));
        Comment comment1 = 저장(수정된_댓글.기본_정보_생성(member1, solution, LocalDateTime.of(2023, 9, 10, 10, 10)));
        Comment comment3 = 저장(생성된_댓글.기본_정보_생성(member1, solution, LocalDateTime.of(2023, 9, 11, 10, 10)));
        저장(생성된_댓글.기본_정보_생성(member2, solution, LocalDateTime.of(2023, 9, 12, 10, 10)));
        저장(생성된_댓글.기본_정보_생성(member2, solution, LocalDateTime.of(2023, 9, 10, 15, 10)));
        Comment comment2 = 저장(생성된_댓글.기본_정보_생성(member1, solution, LocalDateTime.of(2023, 9, 10, 20, 10)));

        Slice<Comment> comments = commentRepository.findMyPageByMemberId(PageRequest.of(0, 3), member1.getId());

        assertAll(
                () -> assertThat(comments.getContent()).containsExactly(comment1, comment2, comment3),
                () -> assertThat(comments.hasNext()).isFalse()
        );
    }


    private Member 저장(Member member) {
        return memberRepository.save(member);
    }

    private Problem 저장(Problem problem) {
        return problemRepository.save(problem);
    }

    private Solution 저장(Solution solution) {
        return solutionRepository.save(solution);
    }

    private Comment 저장(Comment comment) {
        return commentRepository.save(comment);
    }


}