package com.prograngers.backend.service;

import com.prograngers.backend.dto.solution.reqeust.ScarpSolutionRequest;
import com.prograngers.backend.dto.solution.reqeust.UpdateSolutionRequest;
import com.prograngers.backend.dto.solution.reqeust.WriteSolutionRequest;
import com.prograngers.backend.dto.solution.response.CommentWithRepliesResponse;
import com.prograngers.backend.dto.solution.response.MySolutionResponse;
import com.prograngers.backend.dto.solution.response.ProblemResponse;
import com.prograngers.backend.dto.solution.response.RecommendedSolutionResponse;
import com.prograngers.backend.dto.solution.response.ReviewWithRepliesResponse;
import com.prograngers.backend.dto.solution.response.ShowMyLikeSolutionsResponse;
import com.prograngers.backend.dto.solution.response.ShowMySolutionDetailResponse;
import com.prograngers.backend.dto.solution.response.ShowMySolutionListResponse;
import com.prograngers.backend.dto.solution.response.ShowSolutionDetailResponse;
import com.prograngers.backend.dto.solution.response.ShowSolutionListResponse;
import com.prograngers.backend.dto.solution.response.SolutionResponse;
import com.prograngers.backend.dto.solution.response.SolutionTitleAndIdResponse;
import com.prograngers.backend.entity.Likes;
import com.prograngers.backend.entity.badge.Badge;
import com.prograngers.backend.entity.badge.BadgeConstant;
import com.prograngers.backend.entity.comment.Comment;
import com.prograngers.backend.entity.member.Member;
import com.prograngers.backend.entity.problem.JudgeConstant;
import com.prograngers.backend.entity.problem.Problem;
import com.prograngers.backend.entity.review.Review;
import com.prograngers.backend.entity.solution.AlgorithmConstant;
import com.prograngers.backend.entity.solution.DataStructureConstant;
import com.prograngers.backend.entity.solution.LanguageConstant;
import com.prograngers.backend.entity.solution.Solution;
import com.prograngers.backend.entity.sortconstant.SortConstant;
import com.prograngers.backend.exception.badrequest.InvalidPageNumberException;
import com.prograngers.backend.exception.badrequest.InvalidPageSizeException;
import com.prograngers.backend.exception.badrequest.PrivateSolutionException;
import com.prograngers.backend.exception.notfound.MemberNotFoundException;
import com.prograngers.backend.exception.notfound.ProblemNotFoundException;
import com.prograngers.backend.exception.notfound.SolutionNotFoundException;
import com.prograngers.backend.exception.unauthorization.MemberUnAuthorizedException;
import com.prograngers.backend.repository.badge.BadgeRepository;
import com.prograngers.backend.repository.comment.CommentRepository;
import com.prograngers.backend.repository.likes.LikesRepository;
import com.prograngers.backend.repository.member.MemberRepository;
import com.prograngers.backend.repository.problem.ProblemRepository;
import com.prograngers.backend.repository.review.ReviewRepository;
import com.prograngers.backend.repository.solution.SolutionRepository;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@Service
@Slf4j
@Transactional(readOnly = true)
public class SolutionService {

    private final SolutionRepository solutionRepository;
    private final CommentRepository commentRepository;
    private final ReviewRepository reviewRepository;
    private final ProblemRepository problemRepository;
    private final MemberRepository memberRepository;
    private final LikesRepository likesRepository;
    private final BadgeRepository badgeRepository;

    @Transactional
    public Long save(WriteSolutionRequest writeSolutionRequest, Long memberId) {
        Member member = findMemberById(memberId);
        Solution solution = writeSolutionRequest.toSolution(member, save(writeSolutionRequest));
        Long saved = solutionRepository.save(solution).getId();
        addBadge(solutionRepository.countByMember(member), member);
        return saved;
    }

    private void addBadge(Long solutionCount, Member member) {
        BadgeConstant badge = BadgeConstant.getBadge(solutionCount);
        if (badge != null) {
            badgeRepository.save(Badge.builder().member(member).badgeType(badge).build());
        }
    }

    private Problem save(WriteSolutionRequest writeSolutionRequest) {
        String problemLink = writeSolutionRequest.getProblemLink();
        Optional<Problem> problem = problemRepository.findByLink(problemLink);
        if (problem.isPresent()) {
            return problem.get();
        }
        return problemRepository.save(writeSolutionRequest.toProblem(validLink(writeSolutionRequest.getProblemLink())));
    }

    @Transactional
    public Long update(Long solutionId, UpdateSolutionRequest request, Long memberId) {
        Solution target = findSolutionById(solutionId);
        Member member = findMemberById(memberId);
        validMemberAuthorization(target, member);
        Solution solution = request.updateSolution(target);
        Solution updated = solutionRepository.save(solution);
        return updated.getId();
    }

    @Transactional
    public void delete(Long solutionId, Long memberId) {
        Solution target = findSolutionById(solutionId);
        validMemberAuthorization(target, findMemberById(memberId));
        deleteSolutionCommentAndReviews(target);
        solutionRepository.delete(target);
    }

    private void deleteSolutionCommentAndReviews(Solution target) {
        commentRepository.findAllBySolution(target)
                .stream()
                .forEach(comment -> commentRepository.delete(comment));

        reviewRepository.findAllBySolution(target)
                .stream()
                .forEach(review -> reviewRepository.delete(review));
    }

    @Transactional
    public Long scrap(Long scrapTargetId, ScarpSolutionRequest request, Long memberId) {
        Solution solution = request.toSolution(findSolutionById(scrapTargetId), findMemberById(memberId));
        return solutionRepository.save(solution).getId();
    }


    public ShowSolutionDetailResponse getSolutionDetail(Long solutionId, Long memberId) {
        Solution solution = findSolutionById(solutionId);
        Problem problem = solution.getProblem();
        List<Comment> comments = commentRepository.findAllBySolutionOrderByCreatedAtAsc(solution);
        List<Review> reviews = reviewRepository.findAllBySolutionOrderByCodeLineNumberAsc(solution);
        List<Likes> likes = likesRepository.findAllBySolution(solution);
        List<Solution> scrapedSolutions = solutionRepository.findAllByScrapSolution(solution);
        boolean mine = validSolutionIsMine(memberId, solution);
        validViewPrivateSolution(solution, mine);
        boolean pushedLike = validPushedLike(memberId, likes);
        boolean scraped = validScraped(memberId, scrapedSolutions);
        ProblemResponse problemResponse = ProblemResponse.from(problem.getTitle(), problem.getOjName());
        SolutionResponse solutionResponse = SolutionResponse.from(solution, solution.getMember().getNickname(),
                problem.getLink(), likes.size(), scrapedSolutions.size(), pushedLike, scraped, mine,
                getScrapSolutionId(solution));
        List<CommentWithRepliesResponse> commentsResponse = makeCommentsResponse(comments, memberId);
        List<ReviewWithRepliesResponse> reviewsResponse = makeReviewsResponse(reviews, memberId);
        return ShowSolutionDetailResponse.from(problemResponse, solutionResponse, commentsResponse, reviewsResponse);
    }

    public ShowMySolutionDetailResponse getMySolutionDetail(Long memberId, Long solutionId) {
        Solution mainSolution = findSolutionById(solutionId);
        validMemberAuthorization(mainSolution, findMemberById(memberId));
        Problem problem = mainSolution.getProblem();
        List<Solution> solutionList = solutionRepository.findAllByProblemOrderByCreatedAtAsc(problem);
        List<Solution> mySolutionList = getMySolutionList(memberId, solutionList);
        Long likes = likesRepository.countBySolution(mainSolution);
        Long scraps = solutionRepository.countByScrapSolution(mainSolution);
        ProblemResponse problemResponse = ProblemResponse.from(problem.getTitle(), problem.getOjName());
        MySolutionResponse mySolutionResponse = MySolutionResponse.from(mainSolution.getTitle(),
                Arrays.asList(mainSolution.getAlgorithm(), mainSolution.getDataStructure()),
                mainSolution.getDescription(), mainSolution.getCode().split("\n"), likes, scraps);
        List<Comment> mainSolutionComments = commentRepository.findAllBySolutionOrderByCreatedAtAsc(mainSolution);
        List<CommentWithRepliesResponse> mainSolutionCommentsResponse = makeCommentsResponse(mainSolutionComments,
                memberId);
        List<Review> mainSolutionReviews = reviewRepository.findAllBySolutionOrderByCodeLineNumberAsc(mainSolution);
        List<ReviewWithRepliesResponse> mainSolutionReviewResponse = makeReviewsResponse(mainSolutionReviews, memberId);
        List<SolutionTitleAndIdResponse> sideSolutions = getSideSolutions(mySolutionList);
        List<Solution> recommendedSolutions = solutionRepository.findTopLimitsSolutionOfProblemOrderByLikesDesc(problem,
                6);
        List<RecommendedSolutionResponse> recommendedSolutionList = getRecommendedSolutions(recommendedSolutions);
        List<SolutionTitleAndIdResponse> sideScrapSolutions = getSideScrapSolutions(solutionList, memberId);
        return ShowMySolutionDetailResponse.of(problemResponse, mySolutionResponse, mainSolutionCommentsResponse,
                mainSolutionReviewResponse, recommendedSolutionList, sideSolutions, sideScrapSolutions);
    }

    private List<Solution> getMySolutionList(Long memberId, List<Solution> solutionList) {
        return solutionList.stream()
                .filter(solution -> solution.getMember().getId().equals(memberId))
                .collect(Collectors.toList());
    }

    private Solution findSolutionById(Long solutionId) {
        return solutionRepository.findById(solutionId).orElseThrow(SolutionNotFoundException::new);
    }

    private Long getScrapSolutionId(Solution solution) {
        if (solution.getScrapSolution() != null) {
            return solution.getScrapSolution().getId();
        }
        return null;
    }

    private List<CommentWithRepliesResponse> makeCommentsResponse(List<Comment> comments, Long memberId) {
        List<CommentWithRepliesResponse> commentWithRepliesResponseList = new ArrayList<>();

        comments.stream().filter(comment -> comment.getParentId() == null)
                .forEach(comment -> {
                    commentWithRepliesResponseList.add(CommentWithRepliesResponse.of(comment, new ArrayList<>(),
                            checkCommentIsMine(memberId, comment)));
                });
        comments.stream().filter((comment) -> comment.getParentId() != null)
                .forEach((comment) -> {
                    addReplyComments(commentWithRepliesResponseList, comment, memberId);
                });

        return commentWithRepliesResponseList;
    }

    private boolean checkCommentIsMine(Long memberId, Comment comment) {
        return comment.getMember().getId().equals(memberId);
    }

    private void addReplyComments(List<CommentWithRepliesResponse> commentWithRepliesResponseList, Comment comment,
                                  Long memberId) {
        commentWithRepliesResponseList.stream()
                .filter(parentComment -> parentComment.getId().equals(comment.getParentId()))
                .findFirst()
                .get()
                .getReplies()
                .add(CommentWithRepliesResponse.of(comment, checkCommentIsMine(memberId, comment)));
    }

    public ShowSolutionListResponse getSolutionList(
            Pageable pageable, Long problemId, LanguageConstant language, AlgorithmConstant algorithm,
            DataStructureConstant dataStructure, SortConstant sortBy) {
        validPageable(pageable);
        Problem problem = problemRepository.findById(problemId).orElseThrow(ProblemNotFoundException::new);
        PageImpl<Solution> solutions = solutionRepository.getSolutionList(pageable, problem.getId(), language,
                algorithm, dataStructure, sortBy);
        return ShowSolutionListResponse.from(solutions);
    }

    private List<ReviewWithRepliesResponse> makeReviewsResponse(List<Review> mainSolutionReviews, Long memberId) {
        List<ReviewWithRepliesResponse> mainSolutionReviewResponse = mainSolutionReviews.stream()
                .filter(review -> review.getParentId() == null)
                .map(review -> ReviewWithRepliesResponse.from(review, new ArrayList<>(),
                        checkReviewIsMine(memberId, review)))
                .collect(Collectors.toList());
        mainSolutionReviews.stream()
                .filter(review -> review.getParentId() != null)
                .forEach(review -> addReplyReviews(mainSolutionReviewResponse, review, memberId));
        return mainSolutionReviewResponse;
    }

    private static List<SolutionTitleAndIdResponse> getSideSolutions(List<Solution> solutionList) {
        return solutionList.stream()
                .map(solution -> SolutionTitleAndIdResponse.from(solution.getTitle(), solution.getId()))
                .collect(Collectors.toList());
    }

    private List<RecommendedSolutionResponse> getRecommendedSolutions(List<Solution> recommendedSolutions) {
        return recommendedSolutions.stream()
                .map(solution -> makeRecommendedSolutionList(solution))
                .collect(Collectors.toList());
    }


    private List<SolutionTitleAndIdResponse> getSideScrapSolutions(List<Solution> solutions, Long memberId) {
        return solutions.stream()
                .filter(solution -> solution.getScrapSolution() != null)
                .filter(solution -> solution.getMember().getId().equals(memberId))
                .map(solution -> SolutionTitleAndIdResponse.from(solution.getScrapSolution().getTitle(),
                        solution.getScrapSolution().getId()))
                .collect(Collectors.toList());
    }

    private RecommendedSolutionResponse makeRecommendedSolutionList(Solution solution) {
        return RecommendedSolutionResponse.from(solution.getId(), likesRepository.findAllBySolution(solution).size(),
                solution.getTitle(), solution.getMember().getNickname());
    }

    private void addReplyReviews(List<ReviewWithRepliesResponse> mainSolutionReviewResponse, Review review,
                                 Long memberId) {
        mainSolutionReviewResponse.stream()
                .filter(parentReview -> parentReview.getId().equals(review.getParentId()))
                .findFirst()
                .get()
                .getReplies()
                .add(ReviewWithRepliesResponse.from(review, checkReviewIsMine(memberId, review)));
    }

    private boolean checkReviewIsMine(Long memberId, Review review) {
        return review.getMember().getId().equals(memberId);
    }

    private Member findMemberById(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
    }

    private void validMemberAuthorization(Solution target, Member member) {
        if (target.getMember().getId() != member.getId()) {
            throw new MemberUnAuthorizedException();
        }
    }

    private boolean validSolutionIsMine(Long memberId, Solution solution) {
        if (memberId != null) {
            Member member = findMemberById(memberId);
            if (solution.getMember().getId().equals(member.getId())) {
                return true;
            }
        }
        return false;
    }

    private void validViewPrivateSolution(Solution solution, boolean isMine) {
        if (!solution.isPublic() && !isMine) {
            throw new PrivateSolutionException();
        }
    }

    private boolean validScraped(Long memberId, List<Solution> scrapSolutions) {
        return scrapSolutions
                .stream()
                .map(scrapedSolution -> scrapedSolution.getMember().getId())
                .anyMatch(id -> id == memberId);
    }

    private boolean validPushedLike(Long memberId, List<Likes> likes) {
        return likes
                .stream()
                .map(like -> like.getMember().getId())
                .anyMatch(id -> id.equals(memberId));
    }

    private Problem getProblem(WriteSolutionRequest writeSolutionRequest) {
        Optional<Problem> recentProblem = problemRepository.findByLink(writeSolutionRequest.getProblemLink());
        if (!recentProblem.isPresent()) {
            return recentProblem.get();
        }
        JudgeConstant judgeName = validLink(writeSolutionRequest.getProblemLink());
        return writeSolutionRequest.toProblem(judgeName);
    }

    private JudgeConstant validLink(String problemLink) {
        return JudgeConstant.from(problemLink);
    }

    public ShowMySolutionListResponse getMyList(String keyword, LanguageConstant language, AlgorithmConstant algorithm,
                                                DataStructureConstant dataStructure, Integer level, Pageable pageable,
                                                Long memberId) {
        validPageable(pageable);
        Page<Solution> solutions = solutionRepository.getMyList(pageable, keyword, language, algorithm, dataStructure, level, memberId);
        return ShowMySolutionListResponse.from(solutions);
    }

    private void validPageable(Pageable pageable) {
        validPageNumber(pageable);
        validPageSize(pageable);
    }

    private void validPageNumber(Pageable pageable) {
        if (pageable.getPageNumber() < 0) {
            throw new InvalidPageNumberException();
        }
    }

    private void validPageSize(Pageable pageable) {
        if (pageable.getPageSize() < 1) {
            throw new InvalidPageSizeException();
        }
    }

    public ShowMyLikeSolutionsResponse getMyLikes(Long memberId, Pageable pageable) {
        validPageable(pageable);
        Slice<Solution> solutions = solutionRepository.findMyLikesPage(memberId, pageable);
        return ShowMyLikeSolutionsResponse.from(solutions);
    }
}
