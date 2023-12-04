package com.prograngers.backend.controller;

import com.prograngers.backend.controller.auth.LoggedInMember;
import com.prograngers.backend.controller.auth.Login;
import com.prograngers.backend.dto.member.request.UpdateMemberAccountRequest;
import com.prograngers.backend.dto.member.response.ShowMemberAccountResponse;
import com.prograngers.backend.dto.member.response.ShowMemberProfileResponse;
import com.prograngers.backend.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class MemberController {

    private final MemberService memberService;

    @Login
    @GetMapping("/members")
    public ShowMemberAccountResponse showAccount(@LoggedInMember Long memberId) {
        return memberService.getMemberAccount(memberId);
    }

    @Login
    @PatchMapping("/members")
    public ResponseEntity<Void> updateMemberAccount(@LoggedInMember Long memberId,
                                                    @RequestBody UpdateMemberAccountRequest updateMemberAccountRequest) {
        memberService.updateMemberAccount(memberId, updateMemberAccountRequest);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/members/{nickname}")
    public ShowMemberProfileResponse showProfile(@PathVariable String nickname,
                                                 @RequestParam(defaultValue = "9223372036854775807") Long page) {
        return memberService.getMemberProfile(nickname, page);
    }

    @Login
    @DeleteMapping("/members")
    public ResponseEntity<Void> delete(@LoggedInMember Long memberId) {
        memberService.delete(memberId);
        return ResponseEntity.noContent().build();
    }
}
