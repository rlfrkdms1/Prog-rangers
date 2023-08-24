package com.prograngers.backend.controller;

import com.prograngers.backend.controller.auth.LoggedInMember;
import com.prograngers.backend.controller.auth.Login;
import com.prograngers.backend.dto.member.response.MemberProfileResponse;
import com.prograngers.backend.dto.request.UpdateMemberAccountInfoRequest;
import com.prograngers.backend.dto.response.member.MemberAccountInfoResponse;
import com.prograngers.backend.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/prog-rangers")
public class MemberController {

    private final MemberService memberService;
    private static final String MEMBER_ACCOUNT_SETTINGS_REDIRECT_URI = "/prog-rangers/members/mypage/account-settings";

    @GetMapping("/members/mypage/account-settings")
    @Login
    public MemberAccountInfoResponse showAccountInfo(@LoggedInMember Long memberId) {
        return memberService.getMemberAccount(memberId);
    }

    @PostMapping("/members/mypage/account-settings")
    @Login
    public ResponseEntity<Void> updateMemberAccountInfo(@LoggedInMember Long memberId, UpdateMemberAccountInfoRequest updateMemberAccountInfoRequest) {
        memberService.updateMemberAccountInfo(memberId, updateMemberAccountInfoRequest);
        return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(MEMBER_ACCOUNT_SETTINGS_REDIRECT_URI)).build();
    }

    // 타인 프로필 보기
    @GetMapping("/members/profile/{memberId}")
    public ResponseEntity<?> memberProfile(@PathVariable Long memberId){
        MemberProfileResponse memberProfileResponse = memberService.getMemberProfile(memberId);
        return ResponseEntity.ok().body(memberProfileResponse);
    }

}
