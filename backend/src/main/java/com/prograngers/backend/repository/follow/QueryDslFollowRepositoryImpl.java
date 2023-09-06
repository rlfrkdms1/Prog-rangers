package com.prograngers.backend.repository.follow;

import com.prograngers.backend.entity.QFollow;
import com.prograngers.backend.entity.member.Member;
import com.prograngers.backend.entity.member.QMember;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import static com.prograngers.backend.entity.QFollow.*;

@RequiredArgsConstructor
@Repository
@Slf4j
public class QueryDslFollowRepositoryImpl implements  QueryDslFollowRepository {
    private final JPAQueryFactory jpaQueryFactory;
    @Override
    public Long getFollow(Member member) {
    return jpaQueryFactory
            .select(follow.count())
            .from(follow)
            .where(follow.following.eq(member.getId()))
            .fetchOne();
    }
    @Override
    public Long getFollowing(Member member) {
        return jpaQueryFactory
                .select(follow.count())
                .from(follow)
                .where(follow.follower.eq(member.getId()))
                .fetchOne();
    }
}
