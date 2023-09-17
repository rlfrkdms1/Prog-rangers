package com.prograngers.backend.dto.response.member;

import com.prograngers.backend.entity.member.Member;
import com.prograngers.backend.entity.member.MemberType;
import com.prograngers.backend.support.Encrypt;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class MemberAccountInfoResponse {

    private MemberType type;

    private String nickname;

    private String email;

    private String github;

    private String introduction;

    private LocalDate currentlyModifiedAt;

    private String password;

    private String photo;

    public static MemberAccountInfoResponse from(Member member) {
        return MemberAccountInfoResponse.builder()
                .type(member.getType())
                .nickname(member.getNickname())
                .email(member.getEmail())
                .github(member.getGithub())
                .introduction(member.getIntroduction())
                .currentlyModifiedAt(member.getCurrentlyModifiedAt())
                .password(Encrypt.decoding(member.getPassword()))
                .photo(member.getPhoto())
                .build();
    }

}
