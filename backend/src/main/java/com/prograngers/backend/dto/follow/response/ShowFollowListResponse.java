package com.prograngers.backend.dto.follow.response;

import com.prograngers.backend.entity.Follow;
import com.prograngers.backend.entity.member.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Builder
@Getter
@AllArgsConstructor
public class ShowFollowListResponse {
        private List<MemberResponse> followings;
        private List<MemberResponse> followers;
        private List<MemberResponse> recommendedFollowers;

    public static ShowFollowListResponse of(List<Member> followingList, List<Member> followerList, List<Member> recommendedFollows) {
        return ShowFollowListResponse.builder()
                .followings(followingList.stream()
                        .map(MemberResponse::from)
                        .collect(Collectors.toList()))
                .followers(followerList.stream()
                        .map(MemberResponse::from)
                        .collect(Collectors.toList()))
                .recommendedFollowers(recommendedFollows.stream()
                        .map(MemberResponse::from)
                        .collect(Collectors.toList()))
                .build();
    }
}
