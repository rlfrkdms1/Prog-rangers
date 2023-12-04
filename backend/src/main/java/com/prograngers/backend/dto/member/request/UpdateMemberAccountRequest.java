package com.prograngers.backend.dto.member.request;

import com.prograngers.backend.entity.member.Member;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class UpdateMemberAccountRequest {

    private String nickname;

    private String github;

    private String introduction;

    private String oldPassword;

    private String newPassword;

    private String photo;

    public Member toMember() {
        return Member.builder()
                .nickname(this.nickname)
                .github(this.github)
                .introduction(this.introduction)
                .password(this.newPassword)
                .photo(this.photo)
                .build();

    }

}
