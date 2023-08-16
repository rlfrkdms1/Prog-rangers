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

    public SolutionReviewsResponse getReviewDetail(Long solutionId) {

        // solutionId에 해당하는 풀이 찾기
        Solution solution = findById(solutionId);

        // 줄 나눠서 배열에 저장
        String[] lines = solution.getCode().split("\n");

        // 최종 응답 dto에 풀이 내용을 넣는다
        SolutionReviewsResponse solutionReviewsResponse = SolutionReviewsResponse.from(solution, lines);

        // 최종 응답 dto에서 line들을 가져온다
        List<LineResponse> addedLines = solutionReviewsResponse.getLines();

        // 라인들에 대해 for문을 돌면서 리뷰를 추가한다
        for (LineResponse line : addedLines) {
            Integer codeLineNumber = line.getCodeLineNumber();

            // codeLineNumber에 해당하는 review들을 찾는다
            List<Review> reviews = reviewRepository
                    .findAllByCodeLineNumberOrderByDateAsc(codeLineNumber);
            log.info("코드라인 : {}", codeLineNumber);
            List<ReviewResponse> reviewResponses = new ArrayList<>();

            // 해당 라인의 리뷰들에 대해 for문을 돈다
            for (Review review : reviews) {
                log.info("리뷰 id {}, 리뷰 내용 : {}, 부모 리뷰 id : {}", review.getId(), review.getContent(), review.getParentId());

                // 부모가 없는 리뷰인 경우 ReviewResponse dto로 만든다
                if (review.getParentId() == null) {
                    reviewResponses.add(ReviewResponse.from(review));
                }

                // 부모가 있는 리뷰인 경우 Replyresponse dto로 만든다
                else {
                    for (ReviewResponse r : reviewResponses) {
                        if (r.getId().equals(review.getParentId())) {
                            r.getReplies().add(ReplyResponse.from(review));
                        }
                    }
                }
            }
            line.setReviews(reviewResponses);
        }
        solutionReviewsResponse.setLines(addedLines);
        return solutionReviewsResponse;
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
