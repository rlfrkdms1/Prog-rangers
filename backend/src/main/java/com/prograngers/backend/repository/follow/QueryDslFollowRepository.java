package com.prograngers.backend.repository.follow;

import com.prograngers.backend.entity.member.Member;

public interface QueryDslFollowRepository {

    Long getFollowCount(Member member);

    Long getFollowingCount(Member member);
}
