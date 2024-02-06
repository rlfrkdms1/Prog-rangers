package com.prograngers.backend.service;

import static com.prograngers.backend.entity.solution.AlgorithmConstant.DFS;
import static com.prograngers.backend.entity.solution.DataStructureConstant.LIST;
import static com.prograngers.backend.entity.solution.LanguageConstant.JAVA;
import static com.prograngers.backend.support.fixture.MemberFixture.장지담;
import static com.prograngers.backend.support.fixture.ProblemFixture.백준_문제;
import static com.prograngers.backend.support.fixture.ReviewFixture.삭제된_리뷰;
import static com.prograngers.backend.support.fixture.ReviewFixture.생성된_리뷰;
import static com.prograngers.backend.support.fixture.SolutionFixture.공개_풀이;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.prograngers.backend.dto.review.request.UpdateReviewRequest;
import com.prograngers.backend.entity.member.Member;
import com.prograngers.backend.entity.problem.Problem;
import com.prograngers.backend.entity.review.Review;
import com.prograngers.backend.entity.solution.Solution;
import com.prograngers.backend.exception.notfound.ReviewAlreadyDeletedException;
import com.prograngers.backend.exception.unauthorization.MemberUnAuthorizedException;
import com.prograngers.backend.repository.member.MemberRepository;
import com.prograngers.backend.repository.review.ReviewRepository;
import com.prograngers.backend.repository.solution.SolutionRepository;
import java.time.LocalDateTime;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@Slf4j
class ReviewServiceTest {

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private SolutionRepository solutionRepository;

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private ReviewService reviewService;


    @Test
    @DisplayName("자신의 리뷰가 아닌 리뷰를 수정, 삭제하려 할 시 예외가 발생한다")
    void 내_리뷰_아닌_리뷰_수정_삭제_시_예외() {
        // given
        Member member1 = 장지담.아이디_지정_생성(1L);
        Member member2 = 장지담.아이디_지정_생성(2L);
        Problem problem = 백준_문제.기본_정보_생성();
        // 스크랩 당할 풀이 scrapTarget
        Solution solution = 공개_풀이.아이디_지정_생성(1L, problem, member1, LocalDateTime.now(), DFS, LIST, JAVA, 1);

        Review review = 생성된_리뷰.아이디_지정_생성(1L, member1, solution, LocalDateTime.now());

        when(reviewRepository.findById(any())).thenReturn(Optional.of(review));
        when(memberRepository.findById(any())).thenReturn(Optional.of(member2));

        // when then
        assertAll(
                () -> assertThrows(MemberUnAuthorizedException.class,
                        () -> reviewService.updateReview(리뷰_수정_요청_생성(), 2L, 1L)),
                () -> assertThrows(MemberUnAuthorizedException.class, () -> reviewService.deleteReview(2L, 1L))
        );
    }

    @Test
    @DisplayName("삭제된 리뷰를 수정, 삭제하려 할 시 예외가 발생한다")
    void 삭제된_리뷰_수정_삭제_시_예외() {
        // given
        Member member1 = 장지담.아이디_지정_생성(1L);
        Problem problem = 백준_문제.기본_정보_생성();
        // 스크랩 당할 풀이 scrapTarget
        Solution solution = 공개_풀이.아이디_지정_생성(1L, problem, member1, LocalDateTime.now(), DFS, LIST, JAVA, 1);

        Review review = 삭제된_리뷰.아이디_지정_생성(1L, member1, solution, LocalDateTime.now());

        when(reviewRepository.findById(any())).thenReturn(Optional.of(review));
        when(memberRepository.findById(any())).thenReturn(Optional.of(member1));

        // when then
        assertAll(
                () -> assertThrows(ReviewAlreadyDeletedException.class,
                        () -> reviewService.updateReview(리뷰_수정_요청_생성(), 1L, 1L)),
                () -> assertThrows(ReviewAlreadyDeletedException.class, () -> reviewService.deleteReview(1L, 1L))
        );
    }

    private UpdateReviewRequest 리뷰_수정_요청_생성() {
        return new UpdateReviewRequest("수정 내용");
    }

}
