package com.prograngers.backend.controller;

import com.prograngers.backend.controller.auth.LoggedInMember;
import com.prograngers.backend.controller.auth.Login;
import com.prograngers.backend.dto.comment.request.UpdateCommentRequest;
import com.prograngers.backend.dto.comment.request.WriteCommentRequest;
import com.prograngers.backend.dto.comment.response.ShowMyCommentsResponse;
import com.prograngers.backend.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.net.URI;

@RestController
@Slf4j
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class CommentController {

    private final MessageSource messageSource;

    private final CommentService commentService;

    private static final String PAGE_NUMBER_DEFAULT = "1";

    @Login
    @GetMapping("/mypage/comments")
    public ShowMyCommentsResponse showMyComments(@LoggedInMember Long memberId, @RequestParam(defaultValue = PAGE_NUMBER_DEFAULT) Integer pageNumber) {
        return commentService.showMyComments(memberId, pageNumber);
    }

    @Login
    @PostMapping("/solutions/{solutionId}/comments")
    public ResponseEntity<?> writeComment(@PathVariable Long solutionId, @RequestBody @Valid WriteCommentRequest writeCommentRequest,
                                        @LoggedInMember Long memberId) {
        commentService.addComment(solutionId, writeCommentRequest, memberId);
        return redirect(solutionId);
    }

    @Login
    @PatchMapping("/comments/{commentId}")
    public ResponseEntity<?> updateComment(@PathVariable Long commentId,
                                           @RequestBody @Valid UpdateCommentRequest updateCommentRequest,
                                           @LoggedInMember Long memberId) {
        Long solutionId = commentService.updateComment(commentId, updateCommentRequest, memberId);
        return redirect(solutionId);
    }

    @Login
    @DeleteMapping("/solutions/{solutionId}/comments/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable Long solutionId, @PathVariable Long commentId,
                                           @LoggedInMember Long memberId) {
        commentService.deleteComment(commentId, memberId);
        return redirect(solutionId);
    }

    private ResponseEntity<Void> redirect(Long solutionId) {
        return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(getRedirectPath() + solutionId)).build();
    }

    private String getRedirectPath() {
        return messageSource.getMessage("redirect_path", null, null) + "/solutions/";
    }

}
