package com.prograngers.backend.service;

import com.prograngers.backend.dto.solution.SolutionListResponse;
import com.prograngers.backend.dto.comment.CommentReqeust;
import com.prograngers.backend.dto.review.LineResponse;
import com.prograngers.backend.dto.review.ReplyResponse;
import com.prograngers.backend.dto.review.ReviewResponse;
import com.prograngers.backend.dto.review.SolutionReviewsResponse;
import com.prograngers.backend.dto.solution.ScarpSolutionRequest;
import com.prograngers.backend.dto.solution.SolutionDetailResponse;
import com.prograngers.backend.dto.solution.SolutionPatchRequest;
import com.prograngers.backend.dto.solution.SolutionRequest;
import com.prograngers.backend.dto.solution.SolutionUpdateForm;
import com.prograngers.backend.entity.Comment;
import com.prograngers.backend.entity.Member;
import com.prograngers.backend.entity.Problem;
import com.prograngers.backend.entity.Review;
import com.prograngers.backend.entity.Solution;
import com.prograngers.backend.entity.constants.AlgorithmConstant;
import com.prograngers.backend.entity.constants.DataStructureConstant;
import com.prograngers.backend.entity.constants.LanguageConstant;
import com.prograngers.backend.exception.notfound.SolutionNotFoundException;
import com.prograngers.backend.repository.comment.CommentRepository;
import com.prograngers.backend.repository.problem.ProblemRepository;
import com.prograngers.backend.repository.review.ReviewRepository;
import com.prograngers.backend.repository.solution.SolutionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
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

    @Transactional
    public Long save(SolutionRequest solutionRequest) {
        Solution solution = solutionRequest.toEntity();
        Problem problem = problemRepository.findByLink(solution.getProblem().getLink());
        if (problem != null) {
            solution.updateProblem(problem);
        }
        Solution saved = solutionRepository.save(solution);
        return saved.getId();
    }

    @Transactional
    public Long update(Long solutionId, SolutionPatchRequest request) {
        Solution target = findById(solutionId);
        Solution solution = request.toEntity(target);
        Solution updated = solutionRepository.save(solution);
        return updated.getId();
    }

    @Transactional
    public void delete(Long solutionId) throws SolutionNotFoundException {
        Solution target = findById(solutionId);
        List<Comment> comments = commentRepository.findAllBySolution(target);
        for (Comment comment : comments) {
            comment.updateSolution(null);
            commentRepository.delete(comment);
        }
        solutionRepository.delete(target);
    }

    public Solution findById(Long solutionId) {
        return solutionRepository.findById(solutionId).orElseThrow(() -> new SolutionNotFoundException());
    }

    @Transactional
    public Long saveScrap(Long id, ScarpSolutionRequest request) {
        Solution scrap = findById(id);

        // 스크랩 Solution과 사용자가 폼에 입력한 내용을 토대로 새로운 Solution을 만든다
        Solution solution = request.toEntity(scrap);

        Solution saved = solutionRepository.save(solution);

        return saved.getId();
    }

    @Transactional
    public void addComment(Long solutionId, CommentReqeust commentReqeust) {
        Solution solution = findById(solutionId);

        //가상 Member 생성
        Member member = Member.builder().name("멤버이름").nickname("닉네임").build();

        Comment comment = Comment.builder().
                member(member).
                solution(solution).
                orderParent(commentReqeust.getOrderParent()).
                mention(commentReqeust.getMention()).
                content(commentReqeust.getContent()).
                date(LocalDate.now()).parentId(commentReqeust.getParentId()).
                groupNumber(commentReqeust.getGroupNumber()).fixed(false).
                build();

        Comment saved = commentRepository.save(comment);
    }

    public SolutionUpdateForm getUpdateForm(Long solutionId) {
        Solution target = findById(solutionId);
        SolutionUpdateForm solutionUpdateForm = SolutionUpdateForm.toDto(target);
        return solutionUpdateForm;
    }

    public SolutionDetailResponse getSolutionDetail(Long solutionId) {
        Solution solution = findById(solutionId);
        List<Comment> comments = commentRepository.findAllBySolution(solution);
        SolutionDetailResponse solutionDetailResponse = SolutionDetailResponse.toEntity(solution, comments);
        return solutionDetailResponse;
    }

    public SolutionListResponse getSolutionList(
            int page,
            Long problemId,
            LanguageConstant language,
            AlgorithmConstant algorithm,
            DataStructureConstant dataStructure,
            String sortBy) {
        List<Solution> solutions = solutionRepository.getSolutionList(page, problemId, language, algorithm, dataStructure, sortBy);

        return SolutionListResponse.createDto(solutions);
    }
}
