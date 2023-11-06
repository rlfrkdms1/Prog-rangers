package com.prograngers.backend.dto.member.response;

import com.prograngers.backend.entity.member.Member;
import com.prograngers.backend.entity.member.MemberType;
import com.prograngers.backend.support.Encrypt;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ShowBasicMemberAccountResponse extends ShowMemberAccountResponse {

    private String password;
    private LocalDateTime passwordModifiedAt;

    @Builder
    public ShowBasicMemberAccountResponse(MemberType type, String nickname, String email, String github,
                                          String introduction, String photo, String password,
                                          LocalDateTime passwordModifiedAt) {
        this.type = type;
        this.nickname = nickname;
        this.email = email;
        this.github = github;
        this.introduction = introduction;
        this.photo = photo;
        this.password = password;
        this.passwordModifiedAt = passwordModifiedAt;
    }

    public static ShowBasicMemberAccountResponse from(Member member) {
        return ShowBasicMemberAccountResponse.builder()
                .type(member.getType())
                .nickname(member.getNickname())
                .email(member.getEmail())
                .github(member.getGithub())
                .introduction(member.getIntroduction())
                .passwordModifiedAt(member.getPasswordModifiedAt())
                .password(Encrypt.decoding(member.getPassword()))
                .photo(member.getPhoto())
                .build();
    }

}
