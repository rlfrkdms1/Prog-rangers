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

    private final CommentService commentService;
    private static final String PAGE_NUMBER_DEFAULT = "1";

    @Login
    @GetMapping("/mypage/comments")
    public ShowMyCommentsResponse showMyComments(@LoggedInMember Long memberId, @RequestParam(defaultValue = PAGE_NUMBER_DEFAULT) Integer pageNumber) {
        return commentService.showMyComments(memberId, pageNumber);
    }

    @Login
    @PostMapping("/solutions/{solutionId}/comments")
    public ResponseEntity<Void> write(@PathVariable Long solutionId, @RequestBody @Valid WriteCommentRequest request,
                                      @LoggedInMember Long memberId) {
        commentService.addComment(solutionId, request, memberId);
        return ResponseEntity.created(URI.create("/api/v1/solutions/" + solutionId)).build();
    }

    @Login
    @PatchMapping("/comments/{commentId}")
    public ResponseEntity<Void> update(@PathVariable Long commentId,
                                       @RequestBody @Valid UpdateCommentRequest request,
                                       @LoggedInMember Long memberId) {
        commentService.updateComment(commentId, request, memberId);
        return ResponseEntity.noContent().build();
    }

    @Login
    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<Void> delete(@PathVariable Long commentId,
                                       @LoggedInMember Long memberId) {
        commentService.deleteComment(commentId, memberId);
        return ResponseEntity.noContent().build();
    }
}
