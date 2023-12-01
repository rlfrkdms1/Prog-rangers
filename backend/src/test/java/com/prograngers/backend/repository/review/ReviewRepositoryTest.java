package com.prograngers.backend.repository.review;

import static com.prograngers.backend.entity.solution.LanguageConstant.JAVA;
import static com.prograngers.backend.support.fixture.MemberFixture.길가은;
import static com.prograngers.backend.support.fixture.MemberFixture.장지담;
import static com.prograngers.backend.support.fixture.ProblemFixture.백준_문제;
import static com.prograngers.backend.support.fixture.ReviewFixture.생성된_리뷰;
import static com.prograngers.backend.support.fixture.SolutionFixture.공개_풀이;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.prograngers.backend.entity.member.Member;
import com.prograngers.backend.entity.problem.Problem;
import com.prograngers.backend.entity.review.Review;
import com.prograngers.backend.entity.solution.Solution;
import com.prograngers.backend.repository.member.MemberRepository;
import com.prograngers.backend.repository.problem.ProblemRepository;
import com.prograngers.backend.repository.solution.SolutionRepository;
import com.prograngers.backend.support.RepositoryTest;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@RepositoryTest
class ReviewRepositoryTest {

    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ProblemRepository problemRepository;
    @Autowired
    private SolutionRepository solutionRepository;

    @Test
    @DisplayName("회원이 주어졌을 때 해당 회원이 작성한 주어진 달의 리뷰들을 조회할 수 있다.")
    void 월별_리뷰_조회() {
        Member member1 = 저장(길가은.기본_정보_생성());
        Member member2 = 저장(장지담.기본_정보_생성());
        Problem problem = 저장(백준_문제.기본_정보_생성());
        Solution solution = 저장(공개_풀이.기본_정보_생성(problem, member1, LocalDateTime.of(2023, 9, 3, 12, 0), JAVA, 1));

        Integer dayOfReview1 = 저장(
                생성된_리뷰.기본_정보_생성(member1, solution, LocalDateTime.of(2023, 9, 5, 12, 10))).getCreatedAt()
                .getDayOfMonth();
        Integer dayOfReview2 = 저장(
                생성된_리뷰.기본_정보_생성(member1, solution, LocalDateTime.of(2023, 9, 3, 12, 10))).getCreatedAt()
                .getDayOfMonth();
        Integer dayOfReview3 = 저장(
                생성된_리뷰.기본_정보_생성(member1, solution, LocalDateTime.of(2023, 9, 6, 12, 10))).getCreatedAt()
                .getDayOfMonth();
        Integer dayOfReview4 = 저장(
                생성된_리뷰.기본_정보_생성(member1, solution, LocalDateTime.of(2023, 8, 8, 12, 10))).getCreatedAt()
                .getDayOfMonth();
        Integer dayOfReview5 = 저장(
                생성된_리뷰.기본_정보_생성(member2, solution, LocalDateTime.of(2023, 9, 10, 12, 10))).getCreatedAt()
                .getDayOfMonth();

        List<Integer> dayOfWriteReview1 = reviewRepository.findAllByMonth(member1.getId(), 9);
        List<Integer> dayOfWriteReview2 = reviewRepository.findAllByMonth(member2.getId(), 9);

        assertAll(
                () -> assertThat(dayOfWriteReview1).contains(dayOfReview1, dayOfReview2, dayOfReview3)
                        .doesNotContain(dayOfReview4, dayOfReview5),
                () -> assertThat(dayOfWriteReview2).contains(dayOfReview5)
                        .doesNotContain(dayOfReview1, dayOfReview2, dayOfReview3, dayOfReview4)
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

    private Review 저장(Review review) {
        return reviewRepository.save(review);
    }


}