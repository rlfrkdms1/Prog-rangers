package com.prograngers.backend.repository.comment;

import com.prograngers.backend.TestConfig;
import com.prograngers.backend.entity.Comment;
import com.prograngers.backend.entity.Problem;
import com.prograngers.backend.entity.Solution;
import com.prograngers.backend.entity.member.Member;
import com.prograngers.backend.exception.notfound.CommentNotFoundException;
import com.prograngers.backend.repository.member.MemberRepository;
import com.prograngers.backend.repository.problem.ProblemRepository;
import com.prograngers.backend.repository.solution.SolutionRepository;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import static com.prograngers.backend.entity.constants.AlgorithmConstant.BFS;
import static com.prograngers.backend.entity.constants.DataStructureConstant.QUEUE;
import static com.prograngers.backend.entity.constants.LanguageConstant.JAVA;
import static com.prograngers.backend.fixture.CommentFixture.댓글1;
import static com.prograngers.backend.fixture.MemberFixture.길가은1;
import static com.prograngers.backend.fixture.MemberFixture.장지담;
import static com.prograngers.backend.fixture.ProblemFixture.문제1;
import static com.prograngers.backend.fixture.ProblemFixture.백준_문제;
import static com.prograngers.backend.fixture.SolutionFixture.풀이1;


@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
@Slf4j
@Import(TestConfig.class)
class CommentRepositoryTest {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ProblemRepository problemRepository;

    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("댓글을 저장할 수 있다")
    @Test
    void 저장_테스트() {

        // given
        Problem problem1 = 문제_저장(백준_문제.기본_정보_문제_생성());
        Member member = 멤버_저장(장지담.기본_정보_멤버_생성());
        Solution solution =

        Comment comment = 댓글1.댓글_생성(null,solution,member);

        // when
        Comment saved = commentRepository.save(comment);

        // then
        Assertions.assertThat(saved).isEqualTo(comment);
    }

    @DisplayName("댓글을 수정할 수 있다")
    @Test
    void 수정_테스트() {
        // given
        Problem problem1 = 문제_저장(문제1.아이디_값_지정_문제_생성());
        Member member = 길가은1.아이디_값_지정_멤버_생성();
        Solution solution = 풀이1.언어_포함_솔루션_생성(null, problem1, member, BFS, QUEUE, JAVA);

        Comment comment = 댓글1.댓글_생성(null,solution,member);

        comment.updateContent("수정 내용");

        // when
        Comment updated = commentRepository.save(comment);

        // then
        Assertions.assertThat(updated).isEqualTo(comment);
    }

    @DisplayName("댓글을 삭제할 수 있다")
    @Test
    void 삭제_테스트() {
        // given
        Problem problem1 = 문제_저장(문제1.아이디_값_지정_문제_생성());
        Member member = 길가은1.아이디_값_지정_멤버_생성();
        Solution solution = 풀이1.언어_포함_솔루션_생성(null, problem1, member,  BFS, QUEUE, JAVA);

        Comment comment = 댓글1.댓글_생성(null,solution,member);
        Comment saved = commentRepository.save(comment);

        // when
        commentRepository.delete(saved);

        // then
        org.junit.jupiter.api.Assertions.assertThrows(
                CommentNotFoundException.class, () -> commentRepository.findById(saved.getId()).
                        orElseThrow(() -> new CommentNotFoundException()));
    }

    Comment 댓글_저장(Comment comment) {
        return commentRepository.save(comment);
    }

    Problem 문제_저장(Problem problem) {
        return problemRepository.save(problem);
    }

    Member 멤버_저장(Member member) {
        return memberRepository.save(member);
    }
}