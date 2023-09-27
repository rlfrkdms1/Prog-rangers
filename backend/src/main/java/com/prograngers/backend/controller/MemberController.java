package com.prograngers.backend.controller;

import com.prograngers.backend.controller.auth.LoggedInMember;
import com.prograngers.backend.controller.auth.Login;
import com.prograngers.backend.dto.member.response.ShowMemberProfileResponse;
import com.prograngers.backend.dto.request.UpdateMemberAccountInfoRequest;
import com.prograngers.backend.dto.response.member.MemberAccountInfoResponse;
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
@RequestMapping("/prog-rangers")
public class MemberController {

    private final MemberService memberService;
    private static final String MEMBER_ACCOUNT_SETTINGS_REDIRECT_URI = "/prog-rangers/mypage/account-settings";

    @Login
    @GetMapping("/mypage/account-settings")
    public MemberAccountInfoResponse showAccountInfo(@LoggedInMember Long memberId) {
        return memberService.getMemberAccount(memberId);
    }

    @Login
    @PatchMapping("/mypage/account-settings")
    public ResponseEntity<Void> updateMemberAccountInfo(@LoggedInMember Long memberId,@RequestBody UpdateMemberAccountInfoRequest updateMemberAccountInfoRequest) {
        memberService.updateMemberAccountInfo(memberId, updateMemberAccountInfoRequest);
        return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(MEMBER_ACCOUNT_SETTINGS_REDIRECT_URI)).build();
    }

    /**
     *  타인 페이지 보기
     * @param memberId : 멤버 아이디
     * @param page 최초값 : 9223372036854775807
     * @return
     */
    @GetMapping("/members/profile/{memberNickname}")
    public ResponseEntity<?> memberProfile(@PathVariable String memberNickname, @RequestParam(defaultValue = "9223372036854775807") Long page){
        ShowMemberProfileResponse showMemberProfileResponse = memberService.getMemberProfile(memberNickname,page);
        return ResponseEntity.ok().body(showMemberProfileResponse);
    }

}
