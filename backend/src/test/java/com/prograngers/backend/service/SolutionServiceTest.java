package com.prograngers.backend.service;

import static com.prograngers.backend.entity.badge.BadgeConstant.새싹;
import static com.prograngers.backend.entity.solution.AlgorithmConstant.BFS;
import static com.prograngers.backend.entity.solution.AlgorithmConstant.DFS;
import static com.prograngers.backend.entity.solution.DataStructureConstant.ARRAY;
import static com.prograngers.backend.entity.solution.DataStructureConstant.LIST;
import static com.prograngers.backend.entity.solution.DataStructureConstant.QUEUE;
import static com.prograngers.backend.entity.solution.LanguageConstant.JAVA;
import static com.prograngers.backend.entity.sortconstant.SortConstant.NEWEST;
import static com.prograngers.backend.support.fixture.CommentFixture.생성된_댓글;
import static com.prograngers.backend.support.fixture.MemberFixture.길가은;
import static com.prograngers.backend.support.fixture.MemberFixture.장지담;
import static com.prograngers.backend.support.fixture.ProblemFixture.백준_문제;
import static com.prograngers.backend.support.fixture.ReviewFixture.생성된_리뷰;
import static com.prograngers.backend.support.fixture.ReviewFixture.수정된_리뷰;
import static com.prograngers.backend.support.fixture.SolutionFixture.공개_풀이;
import static com.prograngers.backend.support.fixture.SolutionFixture.비공개_풀이;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.prograngers.backend.dto.solution.reqeust.ScarpSolutionRequest;
import com.prograngers.backend.dto.solution.reqeust.UpdateSolutionRequest;
import com.prograngers.backend.dto.solution.reqeust.WriteSolutionRequest;
import com.prograngers.backend.dto.solution.response.CommentWithRepliesResponse;
import com.prograngers.backend.dto.solution.response.MySolutionResponse;
import com.prograngers.backend.dto.solution.response.ProblemResponse;
import com.prograngers.backend.dto.solution.response.RecommendedSolutionResponse;
import com.prograngers.backend.dto.solution.response.ReviewWithRepliesResponse;
import com.prograngers.backend.dto.solution.response.ShowMySolutionDetailResponse;
import com.prograngers.backend.dto.solution.response.ShowSolutionDetailResponse;
import com.prograngers.backend.dto.solution.response.ShowSolutionListResponse;
import com.prograngers.backend.dto.solution.response.SolutionResponse;
import com.prograngers.backend.dto.solution.response.SolutionTitleAndIdResponse;
import com.prograngers.backend.entity.Likes;
import com.prograngers.backend.entity.badge.Badge;
import com.prograngers.backend.entity.comment.Comment;
import com.prograngers.backend.entity.member.Member;
import com.prograngers.backend.entity.problem.Problem;
import com.prograngers.backend.entity.review.Review;
import com.prograngers.backend.entity.solution.AlgorithmConstant;
import com.prograngers.backend.entity.solution.DataStructureConstant;
import com.prograngers.backend.entity.solution.LanguageConstant;
import com.prograngers.backend.entity.solution.Solution;
import com.prograngers.backend.exception.badrequest.PrivateSolutionException;
import com.prograngers.backend.exception.notfound.ProblemLinkNotFoundException;
import com.prograngers.backend.exception.unauthorization.MemberUnAuthorizedException;
import com.prograngers.backend.repository.badge.BadgeRepository;
import com.prograngers.backend.repository.comment.CommentRepository;
import com.prograngers.backend.repository.likes.LikesRepository;
import com.prograngers.backend.repository.member.MemberRepository;
import com.prograngers.backend.repository.problem.ProblemRepository;
import com.prograngers.backend.repository.review.ReviewRepository;
import com.prograngers.backend.repository.solution.SolutionRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

@ExtendWith(MockitoExtension.class)
@Slf4j
class SolutionServiceTest {
    @Mock
    private SolutionRepository solutionRepository;
    @Mock
    private CommentRepository commentRepository;
    @Mock
    private ReviewRepository reviewRepository;
    @Mock
    private MemberRepository memberRepository;
    @Mock
    private LikesRepository likesRepository;
    @Mock
    private ProblemRepository problemRepository;
    @Mock
    private BadgeRepository badgeRepository;
    @InjectMocks
    private SolutionService solutionService;

    @DisplayName("스크랩 해서 풀이를 저장할 수 있다")
    @Test
    void 스크랩_저장_테스트() {
        // given
        Member member = 장지담.아이디_지정_생성(1L);
        Problem problem = 백준_문제.기본_정보_생성();
        // 스크랩 당할 풀이 scrapTarget
        Solution scrapTarget = 공개_풀이.아이디_지정_생성(1L, problem, member, LocalDateTime.now(), DFS, LIST, JAVA, 1);

        // 스크랩 요청 생성
        ScarpSolutionRequest request = 스크랩_풀이_생성_요청_생성("풀이제목", "풀이설명", 5);

        // 스크랩의 결과로 만들어져야 하는 풀이
        Solution scrapResult = request.toSolution(scrapTarget, member);

        when(solutionRepository.findById(any())).thenReturn(Optional.of(scrapTarget));
        when(memberRepository.findById(any())).thenReturn(Optional.of(member));
        when(solutionRepository.save(any())).thenReturn(Optional.of(scrapResult).get());

        // when
        solutionService.scrap(scrapTarget.getId(), request, member.getId());

        // then
        verify(solutionRepository, times(1)).save(any());
    }

    @DisplayName("내 풀이가 아닌 비공개 풀이를 조회하면 예외가 발생한다")
    @Test
    void 비공개_풀이_조회_예외_발생() {
        // given
        Member member1 = 장지담.아이디_지정_생성(1L);
        Member member2 = 장지담.아이디_지정_생성(2L);
        Problem problem = 백준_문제.기본_정보_생성();
        Solution solution = 비공개_풀이.태그_추가_생성(problem, member1, LocalDateTime.now(), BFS, QUEUE, JAVA, 1);

        // when
        when(solutionRepository.findById(solution.getId())).thenReturn(Optional.ofNullable(solution));
        when(memberRepository.findById(member2.getId())).thenReturn(Optional.ofNullable(member2));

        // then
        assertThrows(PrivateSolutionException.class,
                () -> solutionService.getSolutionDetail(solution.getId(), member2.getId()));
    }

    @DisplayName("내 풀이가 아닌 풀이를 수정하려고 하면 예외가 발생한다")
    @Test
    void 내_풀이_아닌_풀이_수정_시_예외_발생() {
        // given
        Member member1 = 장지담.아이디_지정_생성(1L);
        Member member2 = 장지담.아이디_지정_생성(2L);
        Problem problem = 백준_문제.기본_정보_생성();
        Solution solution1 = 공개_풀이.아이디_지정_생성(1L, problem, member1, LocalDateTime.now(), BFS, QUEUE, JAVA, 1);

        UpdateSolutionRequest request = 풀이_수정_요청_생성("수정제목", BFS, ARRAY, "수정코드", "수정설명", 1);

        when(memberRepository.findById(any())).thenReturn(Optional.of(member2));
        when(solutionRepository.findById(any())).thenReturn(Optional.of(solution1));

        // when , then
        assertThrows(
                MemberUnAuthorizedException.class,
                () -> solutionService.update(solution1.getId(), request, member2.getId()
                ));
    }

    @DisplayName("내 풀이가 아닌 풀이를 삭제하려고 하면 예외가 발생한다")
    @Test
    void 내_풀이_아닌_풀이_삭제_시_예외_발생() {
        // given
        Member member1 = 장지담.아이디_지정_생성(1L);
        Member member2 = 장지담.아이디_지정_생성(2L);
        Problem problem = 백준_문제.기본_정보_생성();
        Solution solution1 = 공개_풀이.아이디_지정_생성(1L, problem, member1, LocalDateTime.now(), BFS, QUEUE, JAVA, 1);

        when(memberRepository.findById(any())).thenReturn(Optional.of(member2));
        when(solutionRepository.findById(any())).thenReturn(Optional.of(solution1));

        // when , then
        assertThrows(
                MemberUnAuthorizedException.class,
                () -> solutionService.delete(solution1.getId(), member2.getId()
                ));
    }

    @DisplayName("내 풀이 상세보기가 가능하다")
    @Test
    void getMySolutionDetailTest() {

        //given
        final Long member1Id = 1L;
        final Long member2Id = 2L;
        final Long solutionId = 1L;

        //member
        final Member member1 = 장지담.아이디_지정_생성(member1Id);
        final Member member2 = 장지담.아이디_지정_생성(member2Id);

        //problem
        final Problem problem = 백준_문제.기본_정보_생성();

        //mainSolution
        final Solution myMainSolution = 공개_풀이.아이디_지정_생성(1L, problem, member1, LocalDateTime.now().plusDays(2), JAVA, 3);

        //이 문제에 대한 다른 사람들의 풀이 2개
        final Solution othersSolution1 = 공개_풀이.아이디_지정_생성(2L, problem, member2, LocalDateTime.now().minusDays(50), JAVA,
                3);
        final Solution othersSolution2 = 공개_풀이.아이디_지정_생성(3L, problem, member2, LocalDateTime.now().minusDays(40), JAVA,
                3);

        //이 문제에 대한 내풀이 3개, 하나는 다른 사람의 풀이를 스크랩한 풀이다
        final Solution mySolution1 = 공개_풀이.아이디_지정_생성(4L, problem, member1, LocalDateTime.now().minusDays(30), JAVA, 3);
        final Solution mySolution2 = 공개_풀이.아이디_지정_생성(5L, problem, member1, LocalDateTime.now().minusDays(20), JAVA, 3);
        final Solution mySolution3 = 공개_풀이.스크랩_아이디_지정_생성(6L, member1, LocalDateTime.now().minusDays(10), 3,
                othersSolution1);

        //myMainSolution 댓글
        final Comment comment1 = 생성된_댓글.아이디_지정_생성(1L, member1, myMainSolution, LocalDateTime.now().minusDays(4));
        final Comment comment2 = 생성된_댓글.부모_지정_생성(1L, 2L, member2, myMainSolution, LocalDateTime.now().minusDays(3));
        final Comment comment3 = 생성된_댓글.아이디_지정_생성(3L, member2, myMainSolution, LocalDateTime.now().minusDays(2));
        final Comment comment4 = 생성된_댓글.부모_지정_생성(3L, 4L, member1, myMainSolution, LocalDateTime.now().minusDays(1));

        //myMainSolution 리뷰
        final Review review1 = 생성된_리뷰.아이디_지정_생성(1L, member1, myMainSolution, LocalDateTime.now().minusDays(4));
        final Review review2 = 생성된_리뷰.부모_지정_생성(1L, 2L, member2, myMainSolution, LocalDateTime.now().minusDays(3));
        final Review review3 = 수정된_리뷰.아이디_지정_생성(3L, member1, myMainSolution, LocalDateTime.now().minusDays(2));
        final Review review4 = 수정된_리뷰.부모_지정_생성(3L, 4L, member2, myMainSolution, LocalDateTime.now().minusDays(1));

        when(solutionRepository.findById(solutionId)).thenReturn(Optional.of(myMainSolution));
        when(solutionRepository.findAllByProblemOrderByCreatedAtAsc(problem)).thenReturn(
                Arrays.asList(mySolution3, mySolution2, mySolution1, myMainSolution, othersSolution2, othersSolution1));
        when(likesRepository.countBySolution(myMainSolution)).thenReturn(3L);
        when(solutionRepository.countByScrapSolution(myMainSolution)).thenReturn(2L);
        when(commentRepository.findAllBySolutionOrderByCreatedAtAsc(myMainSolution)).thenReturn(
                Arrays.asList(comment1, comment2, comment3, comment4));
        when(reviewRepository.findAllBySolutionOrderByCodeLineNumberAsc(myMainSolution)).thenReturn(
                Arrays.asList(review1, review2, review3, review4));
        when(solutionRepository.findTopLimitsSolutionOfProblemOrderByLikesDesc(problem, 6)).thenReturn(
                Arrays.asList(othersSolution1, othersSolution2));
        when(memberRepository.findById(member1Id)).thenReturn(Optional.of(member1));

        final List<CommentWithRepliesResponse> expectedComments = Arrays.asList(
                CommentWithRepliesResponse.of(comment1, new ArrayList<>(), true),
                CommentWithRepliesResponse.of(comment3, new ArrayList<>(), false));
        expectedComments.get(0).getReplies().add(CommentWithRepliesResponse.of(comment2, false));
        expectedComments.get(1).getReplies().add(CommentWithRepliesResponse.of(comment4, true));

        final List<ReviewWithRepliesResponse> expectedReviews = Arrays.asList(
                ReviewWithRepliesResponse.from(review1, new ArrayList<>(), true),
                ReviewWithRepliesResponse.from(review3, new ArrayList<>(), true));
        expectedReviews.get(0).getReplies().add(ReviewWithRepliesResponse.from(review2, false));
        expectedReviews.get(1).getReplies().add(ReviewWithRepliesResponse.from(review4, false));

        final List<RecommendedSolutionResponse> expectedRecommendedSolutions = Arrays.asList(
                RecommendedSolutionResponse.from(othersSolution1.getId(), 0, othersSolution1.getTitle(),
                        othersSolution1.getMember().getNickname()),
                RecommendedSolutionResponse.from(othersSolution2.getId(), 0, othersSolution2.getTitle(),
                        othersSolution2.getMember().getNickname())
        );

        final List<SolutionTitleAndIdResponse> expectedSideSolutions = Arrays.asList(
                SolutionTitleAndIdResponse.from(mySolution3.getTitle(), mySolution3.getId()),
                SolutionTitleAndIdResponse.from(mySolution2.getTitle(), mySolution2.getId()),
                SolutionTitleAndIdResponse.from(mySolution1.getTitle(), mySolution1.getId()),
                SolutionTitleAndIdResponse.from(myMainSolution.getTitle(), myMainSolution.getId())
        );

        final List<SolutionTitleAndIdResponse> expectedScrapSolutions = Arrays.asList(
                SolutionTitleAndIdResponse.from(othersSolution1.getTitle(), othersSolution1.getId()));

        //when
        final ShowMySolutionDetailResponse expected = ShowMySolutionDetailResponse.of(
                ProblemResponse.from(problem.getTitle(), problem.getOjName()),
                MySolutionResponse.from(myMainSolution.getTitle(),
                        Arrays.asList(myMainSolution.getAlgorithm(), myMainSolution.getDataStructure()),
                        myMainSolution.getDescription(), myMainSolution.getCode().split("\n"), 3L, 2L),
                expectedComments,
                expectedReviews,
                expectedRecommendedSolutions,
                expectedSideSolutions,
                expectedScrapSolutions
        );
        final ShowMySolutionDetailResponse result = solutionService.getMySolutionDetail(member1Id, solutionId);

        //then
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    @DisplayName("새로운 문제의 풀이를 생성한 후 풀이 id를 반환한다")
    void saveTest() {
        //given
        final Long memberId = 1L;
        final Long solutionId = 1L;
        final Member member = 장지담.아이디_지정_생성(memberId);
        final WriteSolutionRequest request = 풀이_생성_요청_생성("백준 문제", "풀이 제목", "https://www.acmicpc.net/problem/2557", 1,
                JAVA, true, "설명", "import\nmain\nhello\nworld");
        final Problem problem = 백준_문제.기본_정보_생성();
        final Solution expectedSolution = 공개_풀이.아이디_지정_생성(solutionId, problem, member, LocalDateTime.now(), JAVA, 1);

        when(solutionRepository.save(any(Solution.class))).thenReturn(expectedSolution);
        when(problemRepository.findByLink(any())).thenReturn(Optional.empty());
        when(problemRepository.save(any(Problem.class))).thenReturn(problem);
        when(memberRepository.findById(1L)).thenReturn(Optional.of(member));

        //when
        Long resultId = solutionService.save(request, memberId);

        //then

        assertAll(
                () -> assertThat(resultId).isEqualTo(expectedSolution.getId()),
                () -> verify(solutionRepository, times(1)).save(any(Solution.class)),
                () -> verify(problemRepository, times(1)).save(any(Problem.class))
        );
    }

    @Test
    @DisplayName("잘못된 문제 링크로 풀이를 생성하려 할 경우 예외가 발생한다")
    void saveTestWhenInvalidProblemLink() {
        //given
        final Long memberId = 1L;
        final String WRONG_LINK = "wrongLink123";
        Member member = 장지담.아이디_지정_생성(memberId);
        final WriteSolutionRequest request = 풀이_생성_요청_생성("백준 문제", "풀이 제목", WRONG_LINK, 1, JAVA, true, "설명",
                "import\nmain\nhello\nworld");

        when(memberRepository.findById(memberId)).thenReturn(Optional.of(member));
        when(problemRepository.findByLink(any())).thenReturn(Optional.empty());

        //when, then
        assertThrows(ProblemLinkNotFoundException.class, () -> solutionService.save(request, memberId));
    }

    @Test
    @DisplayName("풀이 목록 응답을 할 수 있다")
    void getSolutionListTest() {
        //given
        final Long problemId = 1L;
        final Member member = 장지담.기본_정보_생성();
        final Problem problem = 백준_문제.아이디_지정_생성(problemId);
        Solution solution1 = 공개_풀이.기본_정보_생성(problem, member, LocalDateTime.now().minusDays(3), JAVA, 1);
        Solution solution2 = 공개_풀이.기본_정보_생성(problem, member, LocalDateTime.now().minusDays(2), JAVA, 1);
        Solution solution3 = 공개_풀이.기본_정보_생성(problem, member, LocalDateTime.now().minusDays(1), JAVA, 1);
        Solution solution4 = 공개_풀이.기본_정보_생성(problem, member, LocalDateTime.now(), JAVA, 1);

        when(problemRepository.findById(any())).thenReturn(Optional.of(problem));
        when(solutionRepository.getSolutionList(PageRequest.of(0, 4), problemId, null, null, null, NEWEST)).thenReturn(
                new PageImpl<>(Arrays.asList(solution4, solution3, solution2, solution1), PageRequest.of(0, 4), 4L));

        ShowSolutionListResponse expected = ShowSolutionListResponse.from(
                new PageImpl<>(Arrays.asList(solution4, solution3, solution2, solution1), PageRequest.of(0, 4), 4L), 0);
        //when
        ShowSolutionListResponse result = solutionService.getSolutionList(PageRequest.of(0, 4), problemId, null, null,
                null, NEWEST);
        //then
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    @DisplayName("풀이 상세보기 응답을 할 수 있다")
    void getSolutionDetailTest() {
        //given
        final Long solutionId = 1L;
        final Long memberId = 1L;
        final Long problemId = 1L;

        Member member = 장지담.아이디_지정_생성(memberId);
        Member other = 길가은.아이디_지정_생성(2L);
        Problem problem = 백준_문제.아이디_지정_생성(problemId);
        Solution solution = 공개_풀이.아이디_지정_생성(solutionId, problem, member, LocalDateTime.now().minusDays(100), JAVA, 3);
        Solution scrapSolution = 공개_풀이.스크랩_아이디_지정_생성(2L, other, LocalDateTime.now(), 3, solution);

        Comment comment1 = 생성된_댓글.아이디_지정_생성(1L, member, solution, LocalDateTime.now().minusDays(4));
        Comment comment2 = 생성된_댓글.부모_지정_생성(1L, 2L, other, solution, LocalDateTime.now().minusDays(3));
        Comment comment3 = 생성된_댓글.아이디_지정_생성(3L, member, solution, LocalDateTime.now().minusDays(2));
        Comment comment4 = 생성된_댓글.부모_지정_생성(3L, 4L, other, solution, LocalDateTime.now().minusDays(1));
        Review review1 = 생성된_리뷰.아이디_지정_생성(1L, member, solution, LocalDateTime.now());
        Review review2 = 생성된_리뷰.부모_지정_생성(1L, 2L, other, solution, LocalDateTime.now());
        Review review3 = 수정된_리뷰.아이디_지정_생성(3L, member, solution, LocalDateTime.now());
        Review review4 = 수정된_리뷰.부모_지정_생성(3L, 4L, other, solution, LocalDateTime.now());

        Likes likes = 좋아요_생성(other, solution);

        when(memberRepository.findById(memberId)).thenReturn(Optional.of(member));
        when(solutionRepository.findById(solutionId)).thenReturn(Optional.of(solution));
        when(commentRepository.findAllBySolutionOrderByCreatedAtAsc(solution)).thenReturn(
                List.of(comment1, comment2, comment3, comment4));
        when(likesRepository.findAllBySolution(solution)).thenReturn(List.of(likes));
        when(solutionRepository.findAllByScrapSolution(solution)).thenReturn(List.of(scrapSolution));
        when(reviewRepository.findAllBySolutionOrderByCodeLineNumberAsc(solution)).thenReturn(
                List.of(review1, review2, review3, review4));

        ProblemResponse problemResponse = ProblemResponse.from(problem.getTitle(), problem.getOjName());
        SolutionResponse solutionResponse = SolutionResponse.from(solution, solution.getMember().getNickname(),
                problem.getLink(), 1, 1, false, false, true, null);
        List<CommentWithRepliesResponse> commentsResponse = Arrays.asList(
                CommentWithRepliesResponse.of(comment1, new ArrayList<>(), true),
                CommentWithRepliesResponse.of(comment3, new ArrayList<>(), true));
        commentsResponse.get(0).getReplies().add(CommentWithRepliesResponse.of(comment2, false));
        commentsResponse.get(1).getReplies().add(CommentWithRepliesResponse.of(comment4, false));

        List<ReviewWithRepliesResponse> reviewsResponses = Arrays.asList(
                ReviewWithRepliesResponse.from(review1, new ArrayList<>(), true),
                ReviewWithRepliesResponse.from(review3, new ArrayList<>(), true));
        reviewsResponses.get(0).getReplies().add(ReviewWithRepliesResponse.from(review2, false));
        reviewsResponses.get(1).getReplies().add(ReviewWithRepliesResponse.from(review4, false));

        ShowSolutionDetailResponse expected = ShowSolutionDetailResponse.from(problemResponse, solutionResponse,
                commentsResponse, reviewsResponses);
        //when
        ShowSolutionDetailResponse result = solutionService.getSolutionDetail(solutionId, memberId);
        //then
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    @DisplayName("이미 존재하는 문제로 풀이 생성 시 문제는 저장되지 않는다")
    void saveTestWhenProblemAlreadyExists() {
        //given
        final Long memberId = 1L;
        final Long solutionId = 1L;
        final Member member = 장지담.아이디_지정_생성(memberId);
        final WriteSolutionRequest request = 풀이_생성_요청_생성("백준 문제", "풀이 제목", "https://www.acmicpc.net/problem/2557", 1,
                JAVA, true, "설명", "import\nmain\nhello\nworld");
        final Problem problem = 백준_문제.기본_정보_생성();
        final Solution expectedSolution = 공개_풀이.아이디_지정_생성(solutionId, problem, member, LocalDateTime.now(), JAVA, 1);

        when(solutionRepository.save(any(Solution.class))).thenReturn(expectedSolution);
        when(problemRepository.findByLink(any())).thenReturn(Optional.of(problem));
        when(memberRepository.findById(1L)).thenReturn(Optional.of(member));

        //when
        Long resultId = solutionService.save(request, memberId);

        //then

        assertAll(
                () -> assertThat(resultId).isEqualTo(expectedSolution.getId()),
                () -> verify(solutionRepository, times(1)).save(any(Solution.class)),
                () -> verify(problemRepository, never()).save(any(Problem.class))
        );
    }

    @Test
    void 뱃지_생성_테스트() {
        //given
        final String problemLink = "https://www.acmicpc.net/problem/2557";
        final Long memberId = 1L;
        final Member member = 장지담.아이디_지정_생성(1L);
        final Problem problem = 백준_문제.아이디_지정_생성(1L);
        final Solution solution = 공개_풀이.아이디_지정_생성(1L, problem, member, LocalDateTime.now(), JAVA, 1);
        final WriteSolutionRequest request = 풀이_생성_요청_생성("백준 문제", "풀이 제목", problemLink, 1, JAVA, true, "설명",
                "import\nmain\nhello\nworld");
        final Badge badge = Badge.builder().badgeType(새싹).build();

        when(problemRepository.findByLink(problemLink)).thenReturn(Optional.empty());
        when(problemRepository.save(any(Problem.class))).thenReturn(problem);
        when(memberRepository.findById(memberId)).thenReturn(Optional.of(member));
        when(solutionRepository.save(any(Solution.class))).thenReturn(solution);
        when(solutionRepository.countByMember(member)).thenReturn(1L);

        //when
        solutionService.save(request, memberId);

        //then
        verify(badgeRepository, times(1)).save(any(Badge.class));
    }

    private Likes 좋아요_생성(Member member, Solution solution) {
        return Likes.builder()
                .member(member)
                .solution(solution)
                .build();
    }

    private ScarpSolutionRequest 스크랩_풀이_생성_요청_생성(String title, String description, int level) {
        return new ScarpSolutionRequest(title, description, level);
    }

    private static UpdateSolutionRequest 풀이_수정_요청_생성(
            String title, AlgorithmConstant algorithm, DataStructureConstant dataStructure,
            String code, String description, int level) {
        return new UpdateSolutionRequest(title, algorithm, dataStructure, code, description, level);
    }

    private WriteSolutionRequest 풀이_생성_요청_생성(
            String problemTitle, String solutionTitle, String problemLink,
            Integer level, LanguageConstant language, Boolean isPublic,
            String description, String code
    ) {
        return WriteSolutionRequest.builder()
                .problemTitle(problemTitle)
                .solutionTitle(solutionTitle)
                .problemLink(problemLink)
                .level(level)
                .language(language)
                .isPublic(isPublic)
                .description(description)
                .code(code)
                .build();
    }

}