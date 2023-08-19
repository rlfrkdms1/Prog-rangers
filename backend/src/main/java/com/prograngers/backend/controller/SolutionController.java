package com.prograngers.backend.controller;

import com.prograngers.backend.controller.auth.LoggedInMember;
import com.prograngers.backend.controller.auth.Login;
import com.prograngers.backend.dto.comment.request.CommentPatchRequest;
import com.prograngers.backend.dto.comment.request.CommentReqeust;
import com.prograngers.backend.dto.solution.reqeust.ScarpSolutionPostRequest;
import com.prograngers.backend.dto.solution.response.SolutionDetailResponse;
import com.prograngers.backend.dto.solution.reqeust.SolutionPatchRequest;
import com.prograngers.backend.dto.solution.reqeust.SolutionPostRequest;
import com.prograngers.backend.dto.solution.response.SolutionUpdateFormResponse;
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
@RequestMapping("prog-rangers/solutions")
@Slf4j
public class SolutionController {
    private final SolutionService solutionService;
    private final String REDIRECT_PATH = "http://localhost:8080/prog-rangers/solutions";
    private final String REAL_PATH = "http://13.125.42.167:8080/solutions";

    // solution 쓰기
    @Login
    @PostMapping("/new-form")
    public ResponseEntity<?> newForm(@LoggedInMember Long memberId, @RequestBody @Valid SolutionPostRequest solutionPostRequest) throws URISyntaxException {

        // Valid 확인 -> 검증 실패할 경우 MethodArgumentNotValidException

        // 리포지토리 활용해 저장
        Long saveId = solutionService.save(solutionPostRequest, memberId);

        // 성공할 시 solutiuonId에 해당하는 URI로 리다이렉트, 상태코드 302
        URI redirectUri = new URI(REDIRECT_PATH + "/" + saveId);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(redirectUri);
        return new ResponseEntity<>(headers, HttpStatus.FOUND);
    }

    // scrap해서 생성
    @Login
    @PostMapping("/new-form/{scrapId}")
    public ResponseEntity<?> scrapForm(@LoggedInMember Long memberId, @PathVariable Long scrapId, @RequestBody @Valid  ScarpSolutionPostRequest request)
            throws URISyntaxException {
        // 입력 폼과 스크랩 id로 새로운 Solution 생성
        Long saveId = solutionService.saveScrap(scrapId, request, memberId);

        // 성공할 시 solution 목록으로 리다이렉트, 상태코드 302
        URI redirectUri = new URI(REDIRECT_PATH + "/" + saveId);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(redirectUri);
        return new ResponseEntity<>(headers, HttpStatus.FOUND);
    }

    // 수정 폼 반환
    @Login
    @GetMapping("/{solutionId}/update-form")
    public ResponseEntity<?> updateForm(@PathVariable Long solutionId, @LoggedInMember Long memberId) {
        SolutionUpdateFormResponse updateForm = solutionService.getUpdateForm(solutionId, memberId);
        return ResponseEntity.ok().body(updateForm);
    }

    // 수정 요청
    @Login
    @PatchMapping("/{solutionId}")
    public ResponseEntity<?> update(@PathVariable Long solutionId, @LoggedInMember Long memberId,
                                    @RequestBody @Valid SolutionPatchRequest solutionPatchRequest) throws URISyntaxException {

        // solutionService로 update한다
        Long updateId = solutionService.update(solutionId, solutionPatchRequest, memberId);

        // 성공할 시 solutiuonId에 해당하는 URI로 리다이렉트, 상태코드 302
        URI redirectUri = new URI(REDIRECT_PATH + "/" + updateId);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(redirectUri);
        return new ResponseEntity<>(headers, HttpStatus.FOUND);
    }

    // 삭제 요청
    @Login
    @DeleteMapping("/{solutionId}")
    public ResponseEntity<?> delete(@PathVariable Long solutionId, @LoggedInMember Long memberId) throws URISyntaxException {

        // solutionService로 delete한다
        solutionService.delete(solutionId, memberId);

        // 성공할 시 solution 목록으로 리다이렉트, 상태코드 302
        URI redirectUri = new URI(REDIRECT_PATH);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(redirectUri);
        return new ResponseEntity<>(headers, HttpStatus.FOUND);

    }

    // Solution 상세보기 요청
    @GetMapping("/{solutionId}")
    public ResponseEntity<?> solutionDetail(@PathVariable Long solutionId, @LoggedInMember(required = false) Long memberId) {
        SolutionDetailResponse solutionDetailResponse = solutionService.getSolutionDetail(solutionId, memberId);
        return ResponseEntity.ok().body(solutionDetailResponse);
    }
}
