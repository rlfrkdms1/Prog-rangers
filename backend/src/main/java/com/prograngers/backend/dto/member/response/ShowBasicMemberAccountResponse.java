package com.prograngers.backend.dto.member.response;

import com.prograngers.backend.entity.member.Member;
import com.prograngers.backend.entity.member.MemberType;
import com.prograngers.backend.support.Encrypt;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ShowBasicMemberAccountResponse extends ShowMemberAccountResponse {

    private String password;
    private LocalDate passwordModifiedAt;

    @Builder
    public ShowBasicMemberAccountResponse(MemberType type, String nickname, String email, String github,
                                          String introduction, String photo, String password,
                                          LocalDate passwordModifiedAt) {
        super(type, nickname, email, github, introduction, photo);
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
