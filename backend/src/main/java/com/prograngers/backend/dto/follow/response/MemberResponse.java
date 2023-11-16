package com.prograngers.backend.dto.follow.response;

import com.prograngers.backend.entity.member.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class MemberResponse {
    private Long id;
    private String nickname;
    private String photo;
    private String introduction;

    public static MemberResponse from(Member member) {
        return MemberResponse.builder()
                .id(member.getId())
                .nickname(member.getNickname())
                .photo(member.getPhoto())
                .introduction(member.getIntroduction())
                .build();
    }
}
