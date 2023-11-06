package com.prograngers.backend.dto.member.response;

import com.prograngers.backend.entity.member.MemberType;

public abstract class ShowMemberAccountResponse {

  private MemberType type;
    private String nickname;
    private String email;
    private String github;
    private String introduction;
    private String photo;

    public ShowMemberAccountResponse(MemberType type, String nickname, String email, String github, String introduction, String photo) {
        this.type = type;
        this.nickname = nickname;
        this.email = email;
        this.github = github;
        this.introduction = introduction;
        this.photo = photo;
    }

}
