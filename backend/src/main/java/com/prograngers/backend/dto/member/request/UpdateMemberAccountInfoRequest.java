package com.prograngers.backend.dto.member.request;

import com.prograngers.backend.entity.member.Member;
import lombok.Data;

@Data
public class UpdateMemberAccountInfoRequest {

    private String nickname;

    private String github;

    private String introduction;

    private String oldPassword;

    private String newPassword;

    private String photo;

    public Member toMember() {
        return Member.builder()
                .usable(true)
                .nickname(this.nickname)
                .github(this.github)
                .introduction(this.introduction)
                .password(this.newPassword)
                .photo(this.photo)
                .build();

    }

}
