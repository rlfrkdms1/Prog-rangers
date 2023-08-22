package com.prograngers.backend.service;

import com.prograngers.backend.dto.response.member.MemberAccountInfoResponse;
import com.prograngers.backend.entity.member.Member;
import com.prograngers.backend.exception.notfound.MemberNotFoundException;
import com.prograngers.backend.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberAccountInfoResponse getMemberAccount(Long memberId){
        return MemberAccountInfoResponse.from(findById(memberId));
    }

    public Member findById(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
    }
}
