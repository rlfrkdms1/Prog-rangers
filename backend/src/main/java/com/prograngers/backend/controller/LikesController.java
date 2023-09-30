package com.prograngers.backend.controller;

import com.prograngers.backend.controller.auth.LoggedInMember;
import com.prograngers.backend.controller.auth.Login;
import com.prograngers.backend.service.LikesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
<<<<<<< HEAD
import org.springframework.web.bind.annotation.GetMapping;
=======
import org.springframework.web.bind.annotation.DeleteMapping;
>>>>>>> 1e68fe7e332e0378fce8667f04f28cff021631e1
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class LikesController {

    private final LikesService likesService;

    @Login
<<<<<<< HEAD
    @GetMapping("/{solutionId}/likes/push")
    public ResponseEntity<?> pushLike(@LoggedInMember Long memberId, @PathVariable Long solutionId){
=======
    @PostMapping("/solutions/{solutionId}/likes")
    public ResponseEntity<Void> push(@LoggedInMember Long memberId, @PathVariable Long solutionId){
>>>>>>> 1e68fe7e332e0378fce8667f04f28cff021631e1
        likesService.pushLike(memberId,solutionId);
        return ResponseEntity.created(URI.create("/api/v1/solutions/" + solutionId)).build();
    }
    @Login
<<<<<<< HEAD
    @GetMapping("/{solutionId}/likes/cancel")
    public ResponseEntity<?> cancelLike(@LoggedInMember Long memberId, @PathVariable Long solutionId){
        likesService.cancelLike(memberId,solutionId);
        return redirect(solutionId);
    }
    private ResponseEntity<Object> redirect(Long solutionId) {
        return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(getRedirectPath() + solutionId)).build();
    }

    private String getRedirectPath() {
        return ms.getMessage("redirect_path", null, null) + "/solutions/";
=======
    @DeleteMapping("/solutions/{solutionId}/likes")
    public ResponseEntity<Void> cancel(@LoggedInMember Long memberId, @PathVariable Long solutionId){
        likesService.cancelLike(memberId,solutionId);
        return ResponseEntity.noContent().build();
>>>>>>> 1e68fe7e332e0378fce8667f04f28cff021631e1
    }
}
