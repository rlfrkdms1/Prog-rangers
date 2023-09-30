package com.prograngers.backend.controller;

import com.prograngers.backend.controller.auth.LoggedInMember;
import com.prograngers.backend.controller.auth.Login;
import com.prograngers.backend.dto.member.response.ShowMemberProfileResponse;
import com.prograngers.backend.dto.member.request.UpdateMemberAccountInfoRequest;
import com.prograngers.backend.dto.member.response.ShowMemberAccountInfoResponse;
import com.prograngers.backend.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class MemberController {

    private final MemberService memberService;

    @Login
    @GetMapping("/mypage/account-settings")
    public ShowMemberAccountInfoResponse showAccountInfo(@LoggedInMember Long memberId) {
        return memberService.getMemberAccount(memberId);
    }

    @Login
    @PatchMapping("/mypage/account-settings")
    public ResponseEntity<Void> updateMemberAccountInfo(@LoggedInMember Long memberId,@RequestBody UpdateMemberAccountInfoRequest updateMemberAccountInfoRequest) {
        memberService.updateMemberAccountInfo(memberId, updateMemberAccountInfoRequest);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/members/{nickname}")
    public ShowMemberProfileResponse showProfile(@PathVariable String nickname, @RequestParam(defaultValue = "9223372036854775807") Long page){
        return memberService.getMemberProfile(nickname,page);
    }
}
