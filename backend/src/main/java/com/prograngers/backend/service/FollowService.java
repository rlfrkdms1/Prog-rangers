package com.prograngers.backend.service;

import com.prograngers.backend.entity.Follow;
import com.prograngers.backend.exception.badrequest.AlreadyFollowException;
import com.prograngers.backend.exception.notfound.FollowNotFoundException;
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
        validFollowerAndFollowingExist(followerId, followingId);
        if (followRepository.findByFollowerIdAndFollowingId(followerId, followingId).isPresent()) {
            throw new AlreadyFollowException();
        }
        Follow follow = Follow.builder().followingId(followingId).followerId(followerId).build();
        followRepository.save(follow);
    }

    private void validFollowerAndFollowingExist(Long followerId, Long followingId) {
        if(!memberRepository.existsById(followerId) || !memberRepository.existsById(followingId)) {
            throw new MemberNotFoundException();
        }
    }

    @Transactional
    public void unfollow(Long followerId, Long followingId) {
        validFollowerAndFollowingExist(followerId, followingId);
        Follow follow = followRepository.findByFollowerIdAndFollowingId(followerId, followingId).orElseThrow(FollowNotFoundException::new);
        followRepository.delete(follow);
    }
}
