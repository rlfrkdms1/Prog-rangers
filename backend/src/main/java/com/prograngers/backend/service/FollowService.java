package com.prograngers.backend.service;

import com.prograngers.backend.exception.notfound.MemberNotFoundException;
import com.prograngers.backend.repository.follow.FollowRepository;
import com.prograngers.backend.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class FollowService {

    private final FollowRepository followRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void follow(Long followerId, Long followingId) {
        if(!memberRepository.existsById(followerId) || !memberRepository.existsById(followingId)) {
            throw new MemberNotFoundException();
        }
    }
}
