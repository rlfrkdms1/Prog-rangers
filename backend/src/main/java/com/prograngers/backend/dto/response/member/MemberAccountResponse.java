package com.prograngers.backend.dto.response.member;

import com.prograngers.backend.entity.member.Member;
import com.prograngers.backend.entity.member.MemberType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MemberAccountResponse {

    private MemberType type;

    private String nickname;

    private String email;

    private String github;

    private String introduction;

    private String password;

    private String photo;

    public static MemberAccountResponse from(Member member) {
        return MemberAccountResponse.builder()
                .type(member.getType())
                .nickname(member.getNickname())
                .email(member.getEmail())
                .github(member.getGithub())
                .introduction(member.getIntroduction())
                .password(member.getPassword())
                .photo(member.getPhoto())
                .build();
    }

}
