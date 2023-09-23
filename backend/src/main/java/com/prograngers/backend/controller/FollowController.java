package com.prograngers.backend.controller;

import com.prograngers.backend.controller.auth.LoggedInMember;
import com.prograngers.backend.controller.auth.Login;
import com.prograngers.backend.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class FollowController {

    private final FollowService followService;

    @Login
    @PostMapping("/members/{memberId}/following")
    public ResponseEntity<Void> follow(@LoggedInMember Long followerId,
                                       @PathVariable("memberId") Long followingId) {
        followService.follow(followerId, followingId);
        return ResponseEntity.noContent().build();
    }

}
