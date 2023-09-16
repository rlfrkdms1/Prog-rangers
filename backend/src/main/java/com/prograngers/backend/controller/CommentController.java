package com.prograngers.backend.controller;

import com.prograngers.backend.controller.auth.LoggedInMember;
import com.prograngers.backend.controller.auth.Login;
import com.prograngers.backend.dto.comment.request.CommentPatchRequest;
import com.prograngers.backend.dto.comment.request.CommentRequest;
import com.prograngers.backend.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
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
    private final MessageSource ms;
    private final CommentService commentService;

    // 댓글 작성
    @PostMapping("/{solutionId}/comments")
    @Login
    public ResponseEntity<?> addComment(@PathVariable Long solutionId, @RequestBody @Valid CommentRequest commentRequest,
                                        @LoggedInMember Long memberId) {
        commentService.addComment(solutionId, commentRequest, memberId);
        return redirect(solutionId);
    }

    // 댓글 수정
    @PatchMapping("/comments/{commentId}")
    @Login
    public ResponseEntity<?> updateComment(@PathVariable Long commentId,
                                           @RequestBody @Valid CommentPatchRequest commentPatchRequest,
                                           @LoggedInMember Long memberId) {
        Long solutionId = commentService.updateComment(commentId, commentPatchRequest, memberId);
        // 성공할 시 solutiuonId에 해당하는 URI로 리다이렉트, 상태코드 302
        return redirect(solutionId);
    }

    // 댓글 삭제
    @DeleteMapping("/{solutionId}/comments/{commentId}")
    @Login
    public ResponseEntity<?> deleteComment(@PathVariable Long solutionId, @PathVariable Long commentId,
                                           @LoggedInMember Long memberId) {
        commentService.deleteComment(commentId, memberId);
        // 성공할 시 solutiuonId에 해당하는 URI로 리다이렉트, 상태코드 302
        return redirect(solutionId);
    }

    private ResponseEntity redirect(Long solutionId) {
        // 성공할 시 solutiuonId에 해당하는 URI로 리다이렉트, 상태코드 302
        return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(getRedirectPath() + solutionId)).build();
    }

    private String getRedirectPath() {
        return ms.getMessage("redirect_path", null, null)+"/solutions/";
    }

}
