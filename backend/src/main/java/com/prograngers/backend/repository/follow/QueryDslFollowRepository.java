package com.prograngers.backend.repository.follow;

import com.prograngers.backend.entity.member.Member;

public interface QueryDslFollowRepository {

    Long getFollow(Member member);

    Long getFollowing(Member member);
}
