package com.prograngers.backend.repository.notification;

import static com.prograngers.backend.entity.QNotification.notification;
import static com.prograngers.backend.entity.solution.QSolution.solution;

import com.prograngers.backend.entity.Notification;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class NotificationCustomRepositoryImpl implements NotificationCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Notification> findByMemberIdAndLimit(Long memberId, int limit) {
        return jpaQueryFactory.selectFrom(notification)
                .join(notification.solution, solution).fetchJoin()
                .where(notification.receiver.id.eq(memberId))
                .orderBy(notification.createdAt.desc())
                .limit(limit)
                .fetch();
    }
}
