package com.prograngers.backend.repository.follow;

import com.prograngers.backend.entity.member.Member;
import com.prograngers.backend.entity.member.QMember;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
@Slf4j
public class QueryDslFollowRepositoryImpl implements  QueryDslFollowRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Long getFollow(Member member) {
        return jpaQueryFactory
                .select()
    }

    @Override
    public Long getFollowing(Member member) {
        return null;
    }
}
