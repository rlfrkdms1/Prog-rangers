package com.prograngers.backend.service;

import com.prograngers.backend.dto.solution.response.SolutionListResponse;
import com.prograngers.backend.dto.solution.reqeust.ScarpSolutionPostRequest;
import com.prograngers.backend.dto.solution.response.SolutionDetailResponse;
import com.prograngers.backend.dto.solution.reqeust.SolutionPatchRequest;
import com.prograngers.backend.dto.solution.reqeust.SolutionPostRequest;
import com.prograngers.backend.dto.solution.response.SolutionUpdateFormResponse;
import com.prograngers.backend.entity.comment.Comment;
import com.prograngers.backend.entity.Likes;
import com.prograngers.backend.entity.problem.Problem;
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

import java.util.List;



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




    @Transactional
    public Long save(SolutionPostRequest solutionPostRequest, Long memberId) {
        Member member = getMember(memberId);
        Problem problem = solutionPostRequest.toProblem(problemRepository);
        Solution solution = solutionPostRequest.toSolution(problem,member);
        Solution saved = solutionRepository.save(solution);
        return saved.getId();
    }

    @Transactional
    public Long update(Long solutionId, SolutionPatchRequest request, Long memberId) {
        Solution target = findById(solutionId);
        Member member = getMember(memberId);
        validMemberAuthorization(target, member);
        Solution solution = request.toSolution(target);
        Solution updated = solutionRepository.save(solution);
        return updated.getId();
    }

    @Transactional
    public void delete(Long solutionId, Long memberId){
        Solution target = findById(solutionId);
        Member member = getMember(memberId);
        validMemberAuthorization(target, member);

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
    }
    @Transactional
    public Long saveScrap(Long scrapTargetId, ScarpSolutionPostRequest request, Long memberId) {
        Solution scrap = findById(scrapTargetId);
        Member member = getMember(memberId);

        // 스크랩 Solution과 사용자가 폼에 입력한 내용을 토대로 새로운 Solution을 만든다
        Solution solution = request.toSolution(scrap,member);
        Solution saved = solutionRepository.save(solution);
        return saved.getId();
    }

    public SolutionUpdateFormResponse getUpdateForm(Long solutionId, Long memberId) {
        Solution target = findById(solutionId);
        Member member = getMember(memberId);
        validMemberAuthorization(target,member);
        SolutionUpdateFormResponse solutionUpdateFormResponse = SolutionUpdateFormResponse.toDto(target);
        return solutionUpdateFormResponse;
    }

    public SolutionDetailResponse getSolutionDetail(Long solutionId,Long memberId) {
        Solution solution = findById(solutionId);
        boolean isMine = validSolutionIsMine(memberId, solution);
        validViewPrivateSolution(solution, isMine);
        List<Comment> comments = commentRepository.findAllBySolution(solution);
        List<Likes> likes = likesRepository.findAllBySolution(solution);
        boolean pushedLike = likes.stream().map(like -> like.getMember().getId()).anyMatch(id -> id == memberId);
        List<Solution> scrapSolutions = solutionRepository.findAllByScrapSolution(solution);
        boolean scraped = scrapSolutions.stream().map(scrapedSolution -> scrapedSolution.getMember().getId()).anyMatch(id->id==memberId);
        SolutionDetailResponse solutionDetailResponse = SolutionDetailResponse.from(solution.getProblem(), solution, comments,scraped,scrapSolutions.size(),pushedLike,likes.size(),isMine);
        return solutionDetailResponse;
    }

    public SolutionListResponse getSolutionList(
            Pageable pageable, Long problemId, LanguageConstant language, AlgorithmConstant algorithm, DataStructureConstant dataStructure, SortConstant sortBy) {
        Problem problem = problemRepository.findById(problemId).orElseThrow(ProblemNotFoundException::new);
        PageImpl<Solution> solutions = solutionRepository.getSolutionList(pageable, problem.getId(), language, algorithm, dataStructure, sortBy);
        return SolutionListResponse.from(solutions,pageable.getPageNumber());
    }


    private Member getMember(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
    }

    private static void validMemberAuthorization(Solution target, Member member) {
        if (target.getMember().getId()!= member.getId()){
            throw new MemberUnAuthorizedException();
        }
    }

    private boolean validSolutionIsMine(Long memberId, Solution solution) {
        if (memberId !=null){
            Member member = getMember(memberId);
            if (solution.getMember().getId().equals(member.getId())){
                return true;
            }
        }
        return false;
    }

    private static void validViewPrivateSolution(Solution solution, boolean isMine) {
        if (!solution.isPublic()&&!isMine){
            throw new PrivateSolutionException();
        }
    }
}
