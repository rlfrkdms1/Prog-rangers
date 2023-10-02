package com.prograngers.backend.dto.follow.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class MemberResponse {
    private String nickname;
    private String photo;
    private String description;
}
