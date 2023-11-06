package com.prograngers.backend.dto.member.response;

import com.prograngers.backend.entity.member.Member;
import com.prograngers.backend.entity.member.MemberType;
import com.prograngers.backend.support.Encrypt;
import lombok.Builder;

public class ShowSocialMemberAccountResponse extends ShowMemberAccountResponse {

    @Builder
    ShowSocialMemberAccountResponse(MemberType type, String nickname, String email, String github, String introduction,
                                    String photo) {
        this.type = type;
        this.nickname = nickname;
        this.email = email;
        this.github = github;
        this.introduction = introduction;
        this.photo = photo;
    }

    public static ShowSocialMemberAccountResponse from(Member member) {
        return ShowSocialMemberAccountResponse.builder()
                .type(member.getType())
                .nickname(member.getNickname())
                .email(member.getEmail())
                .github(member.getGithub())
                .introduction(member.getIntroduction())
                .photo(member.getPhoto())
                .build();
    }
}
