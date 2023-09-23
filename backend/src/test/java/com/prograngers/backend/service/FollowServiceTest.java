package com.prograngers.backend.service;

import com.prograngers.backend.exception.badrequest.AlreadyFollowException;
import com.prograngers.backend.repository.follow.FollowRepository;
import com.prograngers.backend.repository.member.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class FollowServiceTest {

    @Mock
    private FollowRepository followRepository;
    @Mock
    private MemberRepository memberRepository;
    @InjectMocks
    private FollowService followService;

    @Test
    @DisplayName("이미 팔로우 내역이 있는 회원을 팔로우 했을 때 예외가 터진다.")
    void 중복_팔로우(){
        Long followerId = 1L;
        Long followingId = 2L;

        given(memberRepository.existsById(followerId)).willReturn(true);
        given(memberRepository.existsById(followingId)).willReturn(true);
        given(followRepository.existsByFollowerIdAndFollowingId(followerId, followingId)).willReturn(true);

        assertAll(
                () -> assertThatThrownBy(() -> followService.follow(followerId, followingId)).isExactlyInstanceOf(AlreadyFollowException.class),
                () -> verify(memberRepository).existsById(followerId),
                () -> verify(memberRepository).existsById(followingId),
                () -> verify(followRepository).existsByFollowerIdAndFollowingId(followerId, followingId)
        );

    }
}