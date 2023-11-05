package com.prograngers.backend.dto.member.response;

import com.prograngers.backend.entity.member.MemberType;
import lombok.Builder;

@Builder
public abstract class ShowMemberAccountResponse {

    private MemberType type;
    private String nickname;
    private String email;
    private String github;
    private String introduction;
    private String photo;
}
