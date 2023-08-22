package com.prograngers.backend.controller;

import com.prograngers.backend.controller.auth.LoggedInMember;
import com.prograngers.backend.dto.response.member.MemberAccountResponse;
import com.prograngers.backend.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/prog-rangers/mypage/account-settings")
public class MemberController {

    private final MemberService memberService;

    @GetMapping
    public MemberAccountResponse showAccountInfo(@LoggedInMember Long memberId) {
        return memberService.getMemberAccount(memberId);
    }

}
