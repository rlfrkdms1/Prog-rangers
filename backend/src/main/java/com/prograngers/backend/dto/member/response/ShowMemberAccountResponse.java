package com.prograngers.backend.dto.member.response;

import com.prograngers.backend.entity.member.MemberType;
import lombok.Getter;

@Getter
public abstract class ShowMemberAccountResponse {

    private final MemberType type;
    private final String nickname;
    private final String email;
    private final String github;
    private final String introduction;
    private final String photo;

    public ShowMemberAccountResponse(MemberType type, String nickname, String email, String github, String introduction,
                                     String photo) {
        this.type = type;
        this.nickname = nickname;
        this.email = email;
        this.github = github;
        this.introduction = introduction;
        this.photo = photo;
    }

}
