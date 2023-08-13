package com.prograngers.backend.controller;

import com.prograngers.backend.dto.SolutionListResponse;
import com.prograngers.backend.dto.comment.CommentPatchRequest;
import com.prograngers.backend.dto.comment.CommentReqeust;
import com.prograngers.backend.dto.review.SolutionReviewsResponse;
import com.prograngers.backend.dto.solution.ScarpSolutionRequest;
import com.prograngers.backend.dto.solution.SolutionDetailResponse;
import com.prograngers.backend.dto.solution.SolutionPatchRequest;
import com.prograngers.backend.dto.solution.SolutionRequest;
import com.prograngers.backend.dto.solution.SolutionUpdateForm;
import com.prograngers.backend.entity.constants.AlgorithmConstant;
import com.prograngers.backend.entity.constants.DataStructureConstant;
import com.prograngers.backend.entity.constants.LanguageConstant;
import com.prograngers.backend.service.CommentService;
import com.prograngers.backend.service.SolutionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/solutions")
@Slf4j
public class SolutionController {
    private final SolutionService solutionService;
    private final CommentService commentService;

    private final String REDIRECT_PATH = "http://localhost:8080/solutions";
    private final String REAL_PATH = "http://13.125.42.167:8080/solutions";

    // solution 쓰기
    @PostMapping("/new-form")
    public ResponseEntity<?> newForm(@RequestBody @Valid SolutionRequest solutionRequest) throws URISyntaxException {

        // Valid 확인 -> 검증 실패할 경우 MethodArgumentNotValidException

        // 리포지토리 활용해 저장
        Long saveId = solutionService.save(solutionRequest.toEntity());

        // 성공할 시 solutiuonId에 해당하는 URI로 리다이렉트, 상태코드 302
        URI redirectUri = new URI(REDIRECT_PATH + "/" + saveId);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(redirectUri);
        return new ResponseEntity<>(headers, HttpStatus.FOUND);
    }

    @PostMapping("/new-form/{scrapId}")
    public ResponseEntity<?> scrapForm(@PathVariable Long scrapId, @RequestBody ScarpSolutionRequest request)
            throws URISyntaxException {
        // 입력 폼과 스크랩 id로 새로운 Solution 생성
        Long saveId = solutionService.saveScrap(scrapId, request);

        // 성공할 시 solution 목록으로 리다이렉트, 상태코드 302
        URI redirectUri = new URI(REDIRECT_PATH + "/" + saveId);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(redirectUri);
        return new ResponseEntity<>(headers, HttpStatus.FOUND);
    }

    // 수정 폼 반환
    @GetMapping("/{solutionId}/update-form")
    public ResponseEntity<?> updateForm(@PathVariable Long solutionId) {
        SolutionUpdateForm updateForm = solutionService.getUpdateForm(solutionId);
        return ResponseEntity.ok().body(updateForm);
    }

    // 수정 요청
    @PatchMapping("/{solutionId}")
    public ResponseEntity<?> update(@PathVariable Long solutionId,
                                    @RequestBody @Valid SolutionPatchRequest solutionPatchRequest) throws URISyntaxException {

        // solutionService로 update한다
        Long updateId = solutionService.update(solutionId, solutionPatchRequest);

        // 성공할 시 solutiuonId에 해당하는 URI로 리다이렉트, 상태코드 302
        URI redirectUri = new URI(REDIRECT_PATH + "/" + updateId);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(redirectUri);
        return new ResponseEntity<>(headers, HttpStatus.FOUND);
    }

    // 삭제 요청
    @DeleteMapping("/{solutionId}")
    public ResponseEntity<?> delete(@PathVariable Long solutionId) throws URISyntaxException {

        // solutionService로 delete한다
        solutionService.delete(solutionId);

        // 성공할 시 solution 목록으로 리다이렉트, 상태코드 302
        URI redirectUri = new URI(REDIRECT_PATH);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(redirectUri);
        return new ResponseEntity<>(headers, HttpStatus.FOUND);

    }

    // Solution 상세보기 요청
    @GetMapping("/{solutionId}")
    public ResponseEntity<?> solutionDetail(@PathVariable Long solutionId) {
        SolutionDetailResponse solutionDetailResponse = solutionService.getSolutionDetail(solutionId);
        return ResponseEntity.ok().body(solutionDetailResponse);
    }


    // 댓글 작성
    @PostMapping("/{solutionId}/comments")
    public ResponseEntity<?> addComment(@PathVariable Long solutionId, @RequestBody CommentReqeust commentReqeust)
            throws URISyntaxException {

        solutionService.addComment(solutionId, commentReqeust);

        // 성공할 시 solutiuonId에 해당하는 URI로 리다이렉트, 상태코드 302
        URI redirectUri = new URI(REDIRECT_PATH + "/" + solutionId);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(redirectUri);
        return new ResponseEntity<>(headers, HttpStatus.FOUND);
    }

    // 댓글 수정
    @PatchMapping("comments/{commentId}")
    public ResponseEntity<?> updateComment(@PathVariable Long commentId,
                                           @RequestBody CommentPatchRequest commentPatchRequest) throws URISyntaxException {

        Long solutionId = commentService.updateComment(commentId, commentPatchRequest);

        // 성공할 시 solutiuonId에 해당하는 URI로 리다이렉트, 상태코드 302
        URI redirectUri = new URI(REDIRECT_PATH + "/" + solutionId);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(redirectUri);
        return new ResponseEntity<>(headers, HttpStatus.FOUND);
    }

    @DeleteMapping("comments/{solutionId}/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable Long solutionId, @PathVariable Long commentId) throws URISyntaxException {
        commentService.deleteComment(commentId);

        // 성공할 시 solutiuonId에 해당하는 URI로 리다이렉트, 상태코드 302
        URI redirectUri = new URI(REDIRECT_PATH + "/" + solutionId);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(redirectUri);
        return new ResponseEntity<>(headers, HttpStatus.FOUND);
    }

    // 상세보기 한 줄 리뷰
    @GetMapping("{solutionId}/reviews")
    public ResponseEntity<?> solutionReviews(@PathVariable Long solutionId){
        SolutionReviewsResponse reviewDetail = solutionService.getReviewDetail(solutionId);
        return ResponseEntity.ok().body(reviewDetail);
    }

    // solution 목록보기
    @GetMapping("{problemId}/solutions")
    public ResponseEntity<?> solutionList(
            @PathVariable Long problemId,
            @RequestParam(required = false) LanguageConstant language,
            @RequestParam(required = false) AlgorithmConstant algorithm,
            @RequestParam(required = false) DataStructureConstant dataStructure,
            @RequestParam(defaultValue = "newest") String sortBy
            ){
        SolutionListResponse solutionListResponse = solutionService.getSolutionList(problemId, language,algorithm,dataStructure,sortBy);
        return ResponseEntity.ok().body(solutionListResponse);
    }
}
