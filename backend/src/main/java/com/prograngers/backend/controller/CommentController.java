package com.prograngers.backend.controller;

import com.prograngers.backend.controller.auth.LoggedInMember;
import com.prograngers.backend.controller.auth.Login;
import com.prograngers.backend.dto.comment.request.CommentPatchRequest;
import com.prograngers.backend.dto.comment.request.CommentReqeust;
import com.prograngers.backend.service.CommentService;
import com.prograngers.backend.service.SolutionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@Slf4j
@RequestMapping("prog-rangers/solutions")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    private final String REDIRECT_PATH = "http://localhost:8080/solutions";
    private final String REAL_PATH = "http://13.125.42.167:8080/solutions";

    // 댓글 작성
    @PostMapping("/{solutionId}")
    @Login
    public ResponseEntity<?> addComment(@PathVariable Long solutionId, @RequestBody CommentReqeust commentReqeust,
                                        @LoggedInMember Long memberId)
            throws URISyntaxException {

        commentService.addComment(solutionId, commentReqeust, memberId);

        // 성공할 시 solutiuonId에 해당하는 URI로 리다이렉트, 상태코드 302
        URI redirectUri = new URI(REDIRECT_PATH + "/" + solutionId);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(redirectUri);
        return new ResponseEntity<>(headers, HttpStatus.FOUND);
    }

    // 댓글 수정
    @PatchMapping("/comments/{commentId}")
    @Login
    public ResponseEntity<?> updateComment(@PathVariable Long commentId,
                                           @RequestBody CommentPatchRequest commentPatchRequest,
                                           @LoggedInMember Long memberId
                                           ) throws URISyntaxException {

        Long solutionId = commentService.updateComment(commentId, commentPatchRequest, memberId);

        // 성공할 시 solutiuonId에 해당하는 URI로 리다이렉트, 상태코드 302
        URI redirectUri = new URI(REDIRECT_PATH + "/" + solutionId);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(redirectUri);
        return new ResponseEntity<>(headers, HttpStatus.FOUND);
    }

    // 댓글 삭제
    @DeleteMapping("/{solutionId}/comments/{commentId}")
    @Login
    public ResponseEntity<?> deleteComment(@PathVariable Long solutionId, @PathVariable Long commentId,
                                           @LoggedInMember Long memberId
    ) throws URISyntaxException {
        commentService.deleteComment(commentId, memberId);

        // 성공할 시 solutiuonId에 해당하는 URI로 리다이렉트, 상태코드 302
        URI redirectUri = new URI(REDIRECT_PATH + "/" + solutionId);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(redirectUri);
        return new ResponseEntity<>(headers, HttpStatus.FOUND);
    }

}
