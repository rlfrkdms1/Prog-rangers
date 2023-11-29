package com.prograngers.backend.repository.follow;

import static com.prograngers.backend.entity.QFollow.follow;

import com.prograngers.backend.entity.member.Member;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
@Slf4j
public class QueryDslFollowRepositoryImpl implements QueryDslFollowRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Long countFollow(Member member) {
        return jpaQueryFactory
                .select(follow.count())
                .from(follow)
                .where(follow.followerId.eq(member.getId()))
                .fetchOne();
    }

    @Override
    public Long countFollowing(Member member) {
        return jpaQueryFactory
                .select(follow.count())
                .from(follow)
                .where(follow.followingId.eq(member.getId()))
                .fetchOne();
    }
}
