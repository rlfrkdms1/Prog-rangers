package com.prograngers.backend.controller;

import com.prograngers.backend.controller.auth.LoggedInMember;
import com.prograngers.backend.controller.auth.Login;
import com.prograngers.backend.service.LikesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/prog-rangers")
public class LikesController {

    private final MessageSource ms;
    private final LikesService likesService;

    @Login
    @GetMapping("/{solutionId}/likes/push")
    public ResponseEntity<?> pushLike(@LoggedInMember Long memberId, @PathVariable Long solutionId){
        likesService.pushLike(memberId,solutionId);
        return redirect(solutionId);
    }
    @Login
    @GetMapping("/{solutionId}/likes/cancel")
    public ResponseEntity<?> cancelLike(@LoggedInMember Long memberId, @PathVariable Long solutionId){
        likesService.cancelLike(memberId,solutionId);
        return redirect(solutionId);
    }
    private ResponseEntity redirect(Long solutionId) {
        return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(getRedirectPath() + solutionId)).build();
    }

    private String getRedirectPath() {
        return ms.getMessage("redirect_path", null, null) + "/solutions/";
    }
}
