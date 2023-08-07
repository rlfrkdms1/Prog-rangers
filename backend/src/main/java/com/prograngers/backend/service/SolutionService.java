package com.prograngers.backend.service;

import com.prograngers.backend.dto.comment.CommentReqeust;
import com.prograngers.backend.dto.review.LineResponse;
import com.prograngers.backend.dto.review.ReplyResponse;
import com.prograngers.backend.dto.review.ReviewResponse;
import com.prograngers.backend.dto.review.SolutionReviewsResponse;
import com.prograngers.backend.dto.solution.ScarpSolutionRequest;
import com.prograngers.backend.dto.solution.SolutionDetailResponse;
import com.prograngers.backend.dto.solution.SolutionPatchRequest;
import com.prograngers.backend.dto.solution.SolutionUpdateForm;
import com.prograngers.backend.entity.Comment;
import com.prograngers.backend.entity.Member;
import com.prograngers.backend.entity.Review;
import com.prograngers.backend.entity.Solution;
import com.prograngers.backend.exception.notfound.SolutionNotFoundException;
import com.prograngers.backend.repository.CommentRepository;
import com.prograngers.backend.repository.ReviewRepository;
import com.prograngers.backend.repository.SolutionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;


@RequiredArgsConstructor
@Service
@Slf4j
@Transactional(readOnly = true)
public class SolutionService {

    private final SolutionRepository solutionRepository;
    private final CommentRepository commentRepository;

    private final ReviewRepository reviewRepository;

    @Transactional
    public Long save(Solution solution) {
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

    public void getReviewDetail(Long solutionId) {
        // solutionId에 해당하는 풀이 찾기
        Solution solution = findById(solutionId);
        // 줄 나눠서 배열에 저장
        String[] lines = solution.getCode().split("\n");

        // 최종 응답 dto
        SolutionReviewsResponse solutionReviewsResponse = new SolutionReviewsResponse();
        solutionReviewsResponse.setTitle(solution.getTitle());
        solutionReviewsResponse.setAlgorithm(solution.getAlgorithm());
        solutionReviewsResponse.setDataStructure(solution.getDataStructure());

        // 먼저 최종 응답 dto에 각 라인을 넣는다
        for (int i=0; i<lines.length; i++){
            LineResponse lineResponse = LineResponse.builder()
                            .codeLineNumber(i+1)
                                    .code(lines[i])
                                            .build();
            solutionReviewsResponse.getLines().add(lineResponse);
        }

        // 각 라인에 리뷰를 넣는다
        List<LineResponse> addedLines = solutionReviewsResponse.getLines();
        for (LineResponse line : addedLines){
            Integer codeLineNumber = line.getCodeLineNumber();
            // codeLineNumber에
            List<Review> reviews = reviewRepository
                    .findAllByCodeLineNumberOrderByDateAsc(codeLineNumber);
            for (Review review : reviews){
                if (review.getParentId() == null){
                    // 부모가 없는 리뷰인 경우
                    ReviewResponse reviewResponse = ReviewResponse.builder()
                            .nickname(review.getMember().getNickname())
                            .photo(review.getMember().getPhoto())
                            .content(review.getContent())
                            .build();
                    line.getReviews().add(reviewResponse);
                } else {
                    // 부모가 있는 리뷰인 경우
                    ReplyResponse replyResponse = ReplyResponse.builder()
                            .nickname(review.getMember().getNickname())
                            .photo(review.getMember().getPhoto())
                            .content(review.getContent())
                            .build();
                    line.getReviews().get(review.getOrderParent()).getReplies().add(replyResponse);
                }
            }
        }


    }
}
