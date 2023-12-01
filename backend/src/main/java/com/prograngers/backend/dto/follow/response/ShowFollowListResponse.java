package com.prograngers.backend.dto.follow.response;

import com.prograngers.backend.entity.member.Member;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class ShowFollowListResponse {
    private List<MemberResponse> followings;
    private List<MemberResponse> followers;
    private List<MemberResponse> recommends;

    public static ShowFollowListResponse of(List<Member> followingList, List<Member> followerList,
                                            List<Member> recommendedFollows) {
        return ShowFollowListResponse.builder()
                .followings(followingList.stream()
                        .map(MemberResponse::from)
                        .collect(Collectors.toList()))
                .followers(followerList.stream()
                        .map(MemberResponse::from)
                        .collect(Collectors.toList()))
                .recommends(recommendedFollows.stream()
                        .map(MemberResponse::from)
                        .collect(Collectors.toList()))
                .build();
    }
}
