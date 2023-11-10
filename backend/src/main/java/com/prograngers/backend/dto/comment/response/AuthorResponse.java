package com.prograngers.backend.dto.comment.response;

import com.prograngers.backend.entity.member.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AuthorResponse {

    private String imageUrl;
    private String nickname;

    public static AuthorResponse from(Member member) {
        return AuthorResponse.builder()
                .nickname(member.getNickname())
                .imageUrl(member.getPhoto())
                .build();
    }

}
