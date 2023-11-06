package com.prograngers.backend.dto.member.response;

import com.prograngers.backend.entity.member.MemberType;
import lombok.Builder;

public abstract class ShowMemberAccountResponse {

    protected MemberType type;
    protected String nickname;
    protected String email;
    protected String github;
    protected String introduction;
    protected String photo;
}
