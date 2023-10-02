package com.prograngers.backend.dto.follow.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
@AllArgsConstructor
public class ShowFollowListResponse {
        private List<MemberResponse> followings;
        private List<MemberResponse> followers;
        private List<MemberResponse> recommendedFollwers;
}
