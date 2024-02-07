package com.prograngers.backend.controller;

import com.prograngers.backend.controller.auth.LoggedInMember;
import com.prograngers.backend.controller.auth.Login;
import com.prograngers.backend.dto.solution.response.ShowMyLikeSolutionsResponse;
import com.prograngers.backend.service.LikesService;
import com.prograngers.backend.service.SolutionService;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class LikesController {

    private final LikesService likesService;
    private final SolutionService solutionService;

    @Login
    @PostMapping("/solutions/{solutionId}/likes")
    public ResponseEntity<Void> push(@LoggedInMember Long memberId, @PathVariable Long solutionId) {
        likesService.pushLike(memberId, solutionId);
        return ResponseEntity.created(URI.create("/api/v1/solutions/" + solutionId)).build();
    }

    @Login
    @DeleteMapping("/solutions/{solutionId}/likes")
    public ResponseEntity<Void> cancel(@LoggedInMember Long memberId, @PathVariable Long solutionId) {
        likesService.cancelLike(memberId, solutionId);
        return ResponseEntity.noContent().build();
    }

    @Login
    @GetMapping("/likes")
    public ShowMyLikeSolutionsResponse showMyLikes(Pageable pageable,
                                                   @LoggedInMember Long memberId) {
        return solutionService.getMyLikes(memberId, pageable);
    }
}
