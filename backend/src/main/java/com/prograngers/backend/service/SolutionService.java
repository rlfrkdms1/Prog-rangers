package com.prograngers.backend.service;

<<<<<<< HEAD
import com.prograngers.backend.dto.solution.response.SolutionListResponse;
import com.prograngers.backend.dto.solution.reqeust.ScarpSolutionPostRequest;
import com.prograngers.backend.dto.solution.response.SolutionDetailResponse;
import com.prograngers.backend.dto.solution.reqeust.SolutionPatchRequest;
import com.prograngers.backend.dto.solution.reqeust.SolutionPostRequest;
import com.prograngers.backend.dto.solution.response.SolutionUpdateFormResponse;
=======
import com.prograngers.backend.dto.solution.reqeust.ScarpSolutionRequest;
import com.prograngers.backend.dto.solution.reqeust.UpdateSolutionRequest;
import com.prograngers.backend.dto.solution.reqeust.WriteSolutionRequest;
import com.prograngers.backend.dto.solution.response.MySolutionResponse;
import com.prograngers.backend.dto.solution.response.ProblemResponse;
import com.prograngers.backend.dto.solution.response.ReviewWithRepliesResponse;
import com.prograngers.backend.dto.solution.response.ShowMySolutionDetailResponse;
import com.prograngers.backend.dto.solution.response.ShowSolutionDetailResponse;
import com.prograngers.backend.dto.solution.response.SolutionTitleAndIdResponse;
import com.prograngers.backend.dto.solution.response.RecommendedSolutionResponse;
import com.prograngers.backend.dto.solution.response.CommentWithRepliesResponse;
import com.prograngers.backend.dto.solution.response.SolutionResponse;
import com.prograngers.backend.dto.solution.response.ShowSolutionListResponse;
>>>>>>> 1e68fe7e332e0378fce8667f04f28cff021631e1
import com.prograngers.backend.entity.comment.Comment;
import com.prograngers.backend.entity.Likes;
import com.prograngers.backend.entity.problem.Problem;
import com.prograngers.backend.entity.review.Review;
import com.prograngers.backend.entity.solution.Solution;
import com.prograngers.backend.entity.solution.AlgorithmConstant;
import com.prograngers.backend.entity.solution.DataStructureConstant;
import com.prograngers.backend.entity.solution.LanguageConstant;
import com.prograngers.backend.entity.sortconstant.SortConstant;
import com.prograngers.backend.entity.member.Member;
import com.prograngers.backend.exception.badrequest.PrivateSolutionException;
import com.prograngers.backend.exception.notfound.MemberNotFoundException;
import com.prograngers.backend.exception.notfound.ProblemNotFoundException;
import com.prograngers.backend.exception.notfound.SolutionNotFoundException;
import com.prograngers.backend.exception.unauthorization.MemberUnAuthorizedException;
import com.prograngers.backend.repository.comment.CommentRepository;
import com.prograngers.backend.repository.likes.LikesRepository;
import com.prograngers.backend.repository.member.MemberRepository;
import com.prograngers.backend.repository.problem.ProblemRepository;
import com.prograngers.backend.repository.review.ReviewRepository;
import com.prograngers.backend.repository.solution.SolutionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

<<<<<<< HEAD
=======
import java.util.ArrayList;
import java.util.Arrays;
>>>>>>> 1e68fe7e332e0378fce8667f04f28cff021631e1
import java.util.List;



@RequiredArgsConstructor
@Service
@Slf4j
@Transactional(readOnly = true)
public class SolutionService {
<<<<<<< HEAD

=======
>>>>>>> 1e68fe7e332e0378fce8667f04f28cff021631e1
    private final SolutionRepository solutionRepository;
    private final CommentRepository commentRepository;
    private final ReviewRepository reviewRepository;
    private final ProblemRepository problemRepository;
    private final MemberRepository memberRepository;
    private final LikesRepository likesRepository;

    @Transactional
<<<<<<< HEAD
    public Long save(SolutionPostRequest solutionPostRequest, Long memberId) {
        Solution solution = solutionPostRequest.toSolution(getProblem(solutionPostRequest),getMember(memberId));
=======
    public Long save(WriteSolutionRequest writeSolutionRequest, Long memberId) {
        Solution solution = writeSolutionRequest.toSolution(getProblem(writeSolutionRequest), findMemberById(memberId));
>>>>>>> 1e68fe7e332e0378fce8667f04f28cff021631e1
        return solutionRepository.save(solution).getId();
    }

    @Transactional
<<<<<<< HEAD
    public Long update(Long solutionId, SolutionPatchRequest request, Long memberId) {
        Solution target = findById(solutionId);
        Member member = getMember(memberId);
        checkMemberAuthorization(target, member);
=======
    public Long update(Long solutionId, UpdateSolutionRequest request, Long memberId) {
        Solution target = findSolutionById(solutionId);
        Member member = findMemberById(memberId);
        validMemberAuthorization(target, member);
>>>>>>> 1e68fe7e332e0378fce8667f04f28cff021631e1
        Solution solution = request.updateSolution(target);
        Solution updated = solutionRepository.save(solution);
        return updated.getId();
    }

    @Transactional
<<<<<<< HEAD
    public void delete(Long solutionId, Long memberId){
        Solution target = findById(solutionId);
        checkMemberAuthorization(target, getMember(memberId));

        commentRepository.findAllBySolution(target)
                        .stream()
                                .forEach(comment->commentRepository.delete(comment));

        reviewRepository.findAllBySolution(target)
                        .stream()
                                .forEach(review->reviewRepository.delete(review));

        solutionRepository.delete(target);
    }

    public Solution findById(Long solutionId) {
        return solutionRepository.findById(solutionId).orElseThrow(SolutionNotFoundException::new);
=======
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
>>>>>>> 1e68fe7e332e0378fce8667f04f28cff021631e1
    }

    @Transactional
<<<<<<< HEAD
    public Long saveScrap(Long scrapTargetId, ScarpSolutionPostRequest request, Long memberId) {
        // 스크랩 Solution과 사용자가 폼에 입력한 내용을 토대로 새로운 Solution을 만든다
        Solution solution = request.toSolution(findById(scrapTargetId),getMember(memberId));
        return solutionRepository.save(solution).getId();
    }

    public SolutionUpdateFormResponse getUpdateForm(Long solutionId, Long memberId) {
        Solution target = findById(solutionId);
        checkMemberAuthorization(target, getMember(memberId));
        return SolutionUpdateFormResponse.toDto(target);
    }

    public SolutionDetailResponse getSolutionDetail(Long solutionId,Long memberId) {
        Solution targetSolution = findById(solutionId);

        // 내 풀이인지 여부
        boolean isMine = checkSolutionIsMine(memberId, targetSolution);

        // 내 풀이가 아닌 비공개 풀이를 열람하는지 확인
        checkViewPrivateSolution(targetSolution, isMine);

        List<Comment> comments = commentRepository.findAllBySolution(targetSolution);
        List<Likes> likes = likesRepository.findAllBySolution(targetSolution);

        // 좋아요 눌렀는지 여부
        boolean pushedLike = checkPushedLike(memberId, likes);

        // 스크랩 풀이들 가져오기
        List<Solution> solutionsScrapedTargetSolution = solutionRepository.findAllByScrapSolution(targetSolution);

        // 스크랩 했는지 여부
        boolean scraped = checkScraped(memberId, solutionsScrapedTargetSolution);

        return SolutionDetailResponse.from(targetSolution, comments,scraped,solutionsScrapedTargetSolution.size(),pushedLike,likes.size(),isMine, memberId);
=======
    public Long saveScrap(Long scrapTargetId, ScarpSolutionRequest request, Long memberId) {
        Solution solution = request.toSolution(findSolutionById(scrapTargetId), findMemberById(memberId));
        return solutionRepository.save(solution).getId();
    }


    public ShowSolutionDetailResponse getSolutionDetail(Long solutionId,Long memberId) {
        // 풀이, 문제, 회원 가져오기
        Solution solution = findSolutionById(solutionId);
        Problem problem = solution.getProblem();
        List<Comment> comments = commentRepository.findAllBySolution(solution);
        List<Likes> likes = likesRepository.findAllBySolution(solution);
        List<Solution> scrapedSolutions = solutionRepository.findAllByScrapSolution(solution);
        boolean mine = validSolutionIsMine(memberId, solution);
        validViewPrivateSolution(solution, mine);
        boolean pushedLike = validPushedLike(memberId, likes);
        boolean scraped = validScraped(memberId, scrapedSolutions);
        ProblemResponse solutionDetailProblem = ProblemResponse.from(problem.getTitle(), problem.getOjName());
        SolutionResponse solutionResponse = SolutionResponse.from(solution, solution.getMember().getNickname(), problem.getLink(), likes.size(), scrapedSolutions.size(), pushedLike, scraped, mine, getScrapSolutionId(solution));
        List<CommentWithRepliesResponse> commentWithRepliesResponse = makeCommentsResponse(comments, memberId);
        return ShowSolutionDetailResponse.from(solutionDetailProblem, solutionResponse, commentWithRepliesResponse);
    }

    public ShowMySolutionDetailResponse getMySolutionDetail(Long memberId, Long solutionId) {
        Solution mainSolution = findSolutionById(solutionId);
        Problem problem = mainSolution.getProblem();
        List<Solution> solutionList = solutionRepository.findAllByProblemOrderByCreatedAtDesc(problem);
        List<Solution> mySolutionList = getMySolutionList(memberId, solutionList);
        Long likes = likesRepository.countBySolution(mainSolution);
        Long scraps = solutionRepository.countByScrapSolution(mainSolution);
        ProblemResponse problemResponse = ProblemResponse.from(problem.getTitle(), problem.getOjName());
        MySolutionResponse mySolutionResponse = MySolutionResponse.from(mainSolution.getTitle(), Arrays.asList(mainSolution.getAlgorithm(), mainSolution.getDataStructure()), mainSolution.getDescription(), mainSolution.getCode().split("\n"), likes, scraps);
        List<Comment> mainSolutionComments = commentRepository.findAllBySolution(mainSolution);
        List<CommentWithRepliesResponse> mainSolutionCommentsResponse = makeCommentsResponse(mainSolutionComments, memberId);
        List<Review> mainSolutionReviews = reviewRepository.findAllBySolution(mainSolution);
        List<ReviewWithRepliesResponse> mainSolutionReviewResponse = makeReviewsResponse(mainSolutionReviews, memberId);
        List<SolutionTitleAndIdResponse> sideSolutions = getSideSolutions(mySolutionList);
        List<Solution> recommendedSolutions = solutionRepository.findTop6SolutionOfProblemOrderByLikesDesc(problem,6);
        List<RecommendedSolutionResponse> recommendedSolutionList = getRecommendedSolutions(recommendedSolutions);
        List<SolutionTitleAndIdResponse> sideScrapSolutions = getSideScrapSolutions(solutionList, memberId);
        return ShowMySolutionDetailResponse.of(problemResponse, mySolutionResponse, mainSolutionCommentsResponse, mainSolutionReviewResponse, recommendedSolutionList, sideSolutions, sideScrapSolutions);
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
                    commentWithRepliesResponseList.add(CommentWithRepliesResponse.of(comment, new ArrayList<>(), checkCommentIsMine(memberId, comment)));
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

    private void addReplyComments(List<CommentWithRepliesResponse> commentWithRepliesResponseList, Comment comment, Long memberId) {
        commentWithRepliesResponseList.stream()
                .filter(parentComment -> parentComment.getId().equals(comment.getParentId()))
                .findFirst()
                .get()
                .getReplies()
                .add(CommentWithRepliesResponse.of(comment, checkCommentIsMine(memberId, comment)));
>>>>>>> 1e68fe7e332e0378fce8667f04f28cff021631e1
    }

    public ShowSolutionListResponse getSolutionList(
            Pageable pageable, Long problemId, LanguageConstant language, AlgorithmConstant algorithm, DataStructureConstant dataStructure, SortConstant sortBy) {
        Problem problem = problemRepository.findById(problemId).orElseThrow(ProblemNotFoundException::new);
        PageImpl<Solution> solutions = solutionRepository.getSolutionList(pageable, problem.getId(), language, algorithm, dataStructure, sortBy);
        return ShowSolutionListResponse.from(solutions, pageable.getPageNumber());
    }

    private List<ReviewWithRepliesResponse> makeReviewsResponse(List<Review> mainSolutionReviews, Long memberId) {
        List<ReviewWithRepliesResponse> mainSolutionReviewResponse = mainSolutionReviews.stream()
                .filter(review -> review.getParentId() == null)
                .map(review -> ReviewWithRepliesResponse.from(review, new ArrayList<>(), checkReviewIsMine(memberId, review)))
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


    private List<SolutionTitleAndIdResponse> getSideScrapSolutions(List<Solution> solutions,Long memberId) {
        return solutions.stream()
                .filter(solution -> solution.getScrapSolution()!=null)
                .filter(solution -> solution.getMember().getId().equals(memberId))
                .map(solution -> SolutionTitleAndIdResponse.from(solution.getScrapSolution().getTitle(), solution.getScrapSolution().getId()))
                .collect(Collectors.toList());
    }

    private RecommendedSolutionResponse makeRecommendedSolutionList(Solution solution) {
        return RecommendedSolutionResponse.from(solution.getId(), likesRepository.findAllBySolution(solution).size(), solution.getTitle(), solution.getMember().getNickname());
    }

    private void addReplyReviews(List<ReviewWithRepliesResponse> mainSolutionReviewResponse, Review review, Long memberId) {
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

    private Member getMember(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
    }

<<<<<<< HEAD
    private static void checkMemberAuthorization(Solution target, Member member) {
        if (target.getMember().getId()!= member.getId()){
=======
    private void validMemberAuthorization(Solution target, Member member) {
        if (target.getMember().getId() != member.getId()) {
>>>>>>> 1e68fe7e332e0378fce8667f04f28cff021631e1
            throw new MemberUnAuthorizedException();
        }
    }

<<<<<<< HEAD
    private boolean checkSolutionIsMine(Long memberId, Solution solution) {
        if (memberId !=null){
            Member member = getMember(memberId);
            if (solution.getMember().getId().equals(member.getId())){
=======
    private boolean validSolutionIsMine(Long memberId, Solution solution) {
        if (memberId != null) {
            Member member = findMemberById(memberId);
            if (solution.getMember().getId().equals(member.getId())) {
>>>>>>> 1e68fe7e332e0378fce8667f04f28cff021631e1
                return true;
            }
        }
        return false;
    }

<<<<<<< HEAD
    private void checkViewPrivateSolution(Solution solution, boolean isMine) {
        if (!solution.isPublic()&&!isMine){
=======
    private void validViewPrivateSolution(Solution solution, boolean isMine) {
        if (!solution.isPublic() && !isMine) {
>>>>>>> 1e68fe7e332e0378fce8667f04f28cff021631e1
            throw new PrivateSolutionException();
        }
    }

    private boolean checkScraped(Long memberId, List<Solution> scrapSolutions) {
        return scrapSolutions
                .stream()
                .map(scrapedSolution -> scrapedSolution.getMember().getId())
                .anyMatch(id -> id == memberId);
    }

    private boolean checkPushedLike(Long memberId, List<Likes> likes) {
        return likes
                .stream()
                .map(like -> like.getMember().getId())
                .anyMatch(id -> id.equals(memberId));
    }

    public Problem getProblem(WriteSolutionRequest writeSolutionRequest) {
        Problem recentProblem = problemRepository.findByLink(writeSolutionRequest.getProblemLink());
        if (recentProblem != null) {
            return recentProblem;
        }
<<<<<<< HEAD
        // 존재하지 않는 문제일 경우 새로운 문제를 만들어서 반환한다, toProblem 내에서 링크 체크함
        return solutionPostRequest.toProblem();
=======
        JudgeConstant judgeName = validLink(writeSolutionRequest.getProblemLink());

        return writeSolutionRequest.toProblem(judgeName);
    }

    private JudgeConstant validLink(String problemLink) {
        return JudgeConstant.from(problemLink);
>>>>>>> 1e68fe7e332e0378fce8667f04f28cff021631e1
    }

}
