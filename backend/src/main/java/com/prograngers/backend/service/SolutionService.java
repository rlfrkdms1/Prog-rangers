package com.prograngers.backend.service;

import com.prograngers.backend.dto.review.response.ReviewWithRepliesResponse;
import com.prograngers.backend.dto.solution.response.MainSolutionResponse;
import com.prograngers.backend.dto.solution.response.ProblemResponse;
import com.prograngers.backend.dto.solution.response.ReviewWIthRepliesResponse;
import com.prograngers.backend.dto.solution.response.ShowMySolutionDetailResponse;
import com.prograngers.backend.dto.solution.response.SolutionTitleAndIdResponse;
import com.prograngers.backend.dto.solution.response.RecommendedSolutionResponse;
import com.prograngers.backend.dto.solution.response.CommentWithRepliesResponse;
import com.prograngers.backend.dto.solution.response.SolutionResponse;
import com.prograngers.backend.dto.solution.response.SolutionListResponse;
import com.prograngers.backend.dto.solution.reqeust.ScarpSolutionRequest;
import com.prograngers.backend.dto.solution.response.ShowSolutionDetailWithProblemAndCommentsResponse;
import com.prograngers.backend.dto.solution.reqeust.UpdateSolutionRequest;
import com.prograngers.backend.dto.solution.reqeust.WriteSolutionRequest;
import com.prograngers.backend.dto.solution.response.ShowSolutionUpdateFormResponse;
import com.prograngers.backend.entity.comment.Comment;
import com.prograngers.backend.entity.Likes;
import com.prograngers.backend.entity.problem.JudgeConstant;
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
import org.springframework.context.MessageSource;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
@Slf4j
@Transactional(readOnly = true)
public class SolutionService {

    private final MessageSource ms;

    private final SolutionRepository solutionRepository;
    private final CommentRepository commentRepository;

    private final ReviewRepository reviewRepository;

    private final ProblemRepository problemRepository;

    private final MemberRepository memberRepository;

    private final LikesRepository likesRepository;

    @Transactional
    public Long save(WriteSolutionRequest writeSolutionRequest, Long memberId) {
        Solution solution = writeSolutionRequest.toSolution(getProblem(writeSolutionRequest),findMemberById(memberId));
        return solutionRepository.save(solution).getId();
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
    public void delete(Long solutionId, Long memberId){
        Solution target = findSolutionById(solutionId);
        validMemberAuthorization(target, findMemberById(memberId));

        commentRepository.findAllBySolution(target)
                        .stream()
                                .forEach(comment->commentRepository.delete(comment));

        reviewRepository.findAllBySolution(target)
                        .stream()
                                .forEach(review->reviewRepository.delete(review));

        solutionRepository.delete(target);
    }

    public Solution findSolutionById(Long solutionId) {
        return solutionRepository.findById(solutionId).orElseThrow(SolutionNotFoundException::new);
    }
    @Transactional
    public Long saveScrap(Long scrapTargetId, ScarpSolutionRequest request, Long memberId) {
        // 스크랩 Solution과 사용자가 폼에 입력한 내용을 토대로 새로운 Solution을 만든다
        Solution solution = request.toSolution(findSolutionById(scrapTargetId),findMemberById(memberId));
        return solutionRepository.save(solution).getId();
    }

    public ShowSolutionUpdateFormResponse getUpdateForm(Long solutionId, Long memberId) {
        Solution target = findSolutionById(solutionId);
        validMemberAuthorization(target, findMemberById(memberId));
        return ShowSolutionUpdateFormResponse.toDto(target);
    }

    public ShowSolutionDetailWithProblemAndCommentsResponse getSolutionDetail(Long solutionId, Long memberId) {
        // 풀이, 문제, 회원 가져오기
        Solution solution = findSolutionById(solutionId);
        Problem problem = solution.getProblem();

        // 댓글 목록, 좋아요 목록 가져오기
        List<Comment> comments = commentRepository.findAllBySolution(solution);
        List<Likes> likes = likesRepository.findAllBySolution(solution);

        // 이 풀이를 스크랩한 풀이들을 가져오기
        List<Solution> scrapedSolutions = solutionRepository.findAllByScrapSolution(solution);

        // 내 풀이인지 여부
        boolean mine = validSolutionIsMine(memberId, solution);

        // 내 풀이가 아닌 비공개 풀이를 열람하는지 확인
        validViewPrivateSolution(solution, mine);

        // 내가 이 풀이를 좋아요 눌렀는지 여부
        boolean pushedLike = validPushedLike(memberId, likes);

        // 내가 이 풀이를 스크랩 했는지 여부
        boolean scraped = validScraped(memberId, scrapedSolutions);

        // 문제 response 만들기
         ProblemResponse solutionDetailProblem = ProblemResponse.from(problem.getTitle(),problem.getOjName());

        // 풀이 response 만들기
        SolutionResponse solutionResponse = SolutionResponse.from(solution,solution.getMember().getNickname(),problem.getLink(),
                likes.size(),scrapedSolutions.size(),pushedLike,scraped,mine,getScrapSolutionLink(solution));

        // 댓글 response 만들기
        List<CommentWithRepliesResponse> commentWithRepliesRespons = makeCommentsResponse(comments, memberId);

        return ShowSolutionDetailWithProblemAndCommentsResponse.from(solutionDetailProblem, solutionResponse, commentWithRepliesRespons);
    }

    private String getScrapSolutionLink(Solution solution){
        if (solution.getScrapSolution()!=null){
            return ms.getMessage("redirect_path", null, null)+"/solutions"+solution.getScrapSolution().getId();
        }
        return null;
    }

    private List<CommentWithRepliesResponse> makeCommentsResponse(List<Comment> comments, Long memberId) {
        // 먼저 부모가 없는 댓글들을 전부 더한다
        List<CommentWithRepliesResponse> commentWithRepliesResponseList = new ArrayList<>();

                comments.stream().filter(comment -> comment.getParentId()==null)
                                .forEach(comment -> {
                                    commentWithRepliesResponseList.add(CommentWithRepliesResponse.from(comment,new ArrayList<>(), checkCommentIsMine(memberId,comment)));
                                });

        // 부모가 있는 댓글들을 더한다
        comments.stream().filter((comment)->comment.getParentId()!=null)
                .forEach((comment)->{
                    addReplyComments(commentWithRepliesResponseList, comment, memberId);
                });

        return commentWithRepliesResponseList;
    }

    private boolean checkCommentIsMine(Long memberId, Comment comment) {
        return comment.getMember().getId().equals(memberId);
    }

    private void addReplyComments(List<CommentWithRepliesResponse> commentWithRepliesResponseList, Comment comment, Long memberId) {
        commentWithRepliesResponseList.stream()
                .filter(parentComment->parentComment.getId().equals(comment.getParentId()))
                .findFirst()
                .get()
                .getReplies()
                .add(CommentWithRepliesResponse.from(comment, checkCommentIsMine(memberId,comment)));
    }

    public SolutionListResponse getSolutionList(
            Pageable pageable, Long problemId, LanguageConstant language, AlgorithmConstant algorithm, DataStructureConstant dataStructure, SortConstant sortBy) {
        Problem problem = problemRepository.findById(problemId).orElseThrow(ProblemNotFoundException::new);
        PageImpl<Solution> solutions = solutionRepository.getSolutionList(pageable, problem.getId(), language, algorithm, dataStructure, sortBy);
        return SolutionListResponse.from(solutions,pageable.getPageNumber());
    }

    // 내 풀이 보여주기
    public ShowMySolutionDetailResponse getMySolutionDetail(Long memberId, Long solutionId) {
        findMemberById(memberId);
        Solution mainSolution = findSolutionById(solutionId);
        List<Solution> solutionList = solutionRepository.findAllByProblem(mainSolution.getProblem());
        Problem problem = mainSolution.getProblem();
        int likes = likesRepository.findAllBySolution(mainSolution).size();
        int scraps = solutionRepository.findAllByScrapSolution(mainSolution).size();
        ProblemResponse problemResponse = ProblemResponse.from(problem.getTitle(), problem.getOjName());
        MainSolutionResponse mainSolutionResponse = MainSolutionResponse.from(mainSolution.getTitle(),Arrays.asList(mainSolution.getAlgorithm(),mainSolution.getDataStructure()), mainSolution.getDescription(), mainSolution.getCode().split("\n"), likes, scraps);
        List<Comment> mainSolutionComments = commentRepository.findAllBySolution(mainSolution);
        List<CommentWithRepliesResponse> mainSolutionCommentsResponse = getMainSolutionCommentsResponse(memberId, mainSolutionComments);
        List<Review> mainSolutionReviews = reviewRepository.findAllBySolution(mainSolution);
        List<ReviewWIthRepliesResponse> mainSolutionReviewResponse = getMainSolutionReviewResponse(memberId, mainSolutionReviews);
        List<SolutionTitleAndIdResponse> sideSolutions = getSideSolutions(solutionList);
        List<Solution> recommendedSolutions = solutionRepository.findTop6SolutionOfProblemOrderByLikesDesc(problem.getId());
        List<RecommendedSolutionResponse> recommendedSolutionList = getRecommendedSolutions(recommendedSolutions);
        List<SolutionTitleAndIdResponse> sideScrapSolutions  = getSideScrapSolutions(problem);
        return ShowMySolutionDetailResponse.from(problemResponse,mainSolutionResponse,mainSolutionCommentsResponse,mainSolutionReviewResponse,recommendedSolutionList,sideSolutions,sideScrapSolutions);
    }

    private List<CommentWithRepliesResponse> getMainSolutionCommentsResponse(Long memberId, List<Comment> mainSolutionComments) {
        List<CommentWithRepliesResponse> mainSolutionCommentsResponse = mainSolutionComments.stream().filter((comment -> comment.getParentId()==null))
                .map(comment -> CommentWithRepliesResponse.from(comment, new ArrayList<>(),checkCommentIsMine(memberId, comment)))
                .collect(Collectors.toList());
        mainSolutionComments.stream().filter(comment -> comment.getParentId()!=null)
                .forEach(comment -> addReplyComments(mainSolutionCommentsResponse,comment, memberId));
        return mainSolutionCommentsResponse;
    }

    private List<ReviewWIthRepliesResponse> getMainSolutionReviewResponse(Long memberId, List<Review> mainSolutionReviews) {
        List<ReviewWIthRepliesResponse> mainSolutionReviewResponse = mainSolutionReviews.stream()
                .filter(review -> review.getParentId() == null)
                .map(review -> ReviewWIthRepliesResponse.from(review, new ArrayList<>(), checkReviewIsMine(memberId, review)))
                .collect(Collectors.toList());
        mainSolutionReviews.stream()
                .filter(review -> review.getParentId()!=null)
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

    private List<SolutionTitleAndIdResponse> getSideScrapSolutions(Problem problem) {
        return solutionRepository.findAllByProblem(problem)
                .stream().filter(solution -> solution.getScrapSolution() != null)
                .map(solution -> SolutionTitleAndIdResponse.from(solution.getScrapSolution().getTitle(), solution.getScrapSolution().getId()))
                .collect(Collectors.toList());
    }

    private RecommendedSolutionResponse makeRecommendedSolutionList(Solution solution) {
        return RecommendedSolutionResponse.from(solution.getId(),likesRepository.findAllBySolution(solution).size(),solution.getTitle(),solution.getMember().getNickname());
    }

    private void addReplyReviews(List<ReviewWIthRepliesResponse> mainSolutionReviewResponse, Review review, Long memberId) {
        mainSolutionReviewResponse.stream()
                .filter(parentReview->parentReview.getId().equals(review.getParentId()))
                .findFirst()
                .get()
                .getReplies()
                .add(ReviewWIthRepliesResponse.from(review, checkReviewIsMine(memberId,review)));
    }

    private boolean checkReviewIsMine(Long memberId, Review review) {
        return review.getMember().getId().equals(memberId);
    }

    private Member findMemberById(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
    }

    private void validMemberAuthorization(Solution target, Member member) {
        if (target.getMember().getId()!= member.getId()){
            throw new MemberUnAuthorizedException();
        }
    }

    private boolean validSolutionIsMine(Long memberId, Solution solution) {
        if (memberId !=null){
            Member member = findMemberById(memberId);
            if (solution.getMember().getId().equals(member.getId())){
                return true;
            }
        }
        return false;
    }

    private void validViewPrivateSolution(Solution solution, boolean isMine) {
        if (!solution.isPublic()&&!isMine){
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

    public Problem getProblem(WriteSolutionRequest writeSolutionRequest){
        // 입력한 문제가 이미 존재하는 문제인지 확인
        Problem recentProblem = problemRepository.findByLink(writeSolutionRequest.getProblemLink());
        // 존재하는 문제일 경우
        if (recentProblem!=null){
            return recentProblem;
        }
        // 존재하지 않는 문제일 경우 새로운 문제를 만들어서 반환한다
        // 링크 검증
        JudgeConstant judgeName = validLink(writeSolutionRequest.getProblemLink());

        return writeSolutionRequest.toProblem(judgeName);
    }

    private JudgeConstant validLink(String problemLink) {
        return JudgeConstant.from(problemLink);
    }

}
