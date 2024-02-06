package com.prograngers.backend.repository.follow;

import com.prograngers.backend.entity.member.Member;

public interface QueryDslFollowRepository {

    Long countFollow(Member member);

    Long countFollowing(Member member);
}
