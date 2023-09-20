package com.prograngers.backend.dto.request;

import com.prograngers.backend.entity.member.Member;
import lombok.Data;

@Data
public class UpdateMemberAccountInfoRequest {

    private String nickname;

    private String github;

    private String introduction;

    private String oldPassword;

    private String password;

    private String photo;

    public Member toMember() {
        return Member.builder()
                .nickname(this.nickname)
                .github(this.github)
                .introduction(this.introduction)
                .password(this.password)
                .photo(this.photo)
                .build();

    }

}
