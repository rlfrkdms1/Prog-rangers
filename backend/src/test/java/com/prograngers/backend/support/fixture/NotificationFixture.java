package com.prograngers.backend.support.fixture;

import static com.prograngers.backend.entity.NotificationType.COMMENT;
import static com.prograngers.backend.entity.NotificationType.REVIEW;

import com.prograngers.backend.entity.Notification;
import com.prograngers.backend.entity.Notification.NotificationBuilder;
import com.prograngers.backend.entity.NotificationType;
import com.prograngers.backend.entity.comment.Comment;
import com.prograngers.backend.entity.member.Member;
import com.prograngers.backend.entity.review.Review;
import com.prograngers.backend.entity.solution.Solution;
import java.time.LocalDateTime;

public enum NotificationFixture {

    리뷰_알림(REVIEW),
    댓글_알림(COMMENT);

    private final NotificationType type;

    NotificationFixture(NotificationType type) {
        this.type = type;
    }

    public NotificationBuilder 기본_빌더_생성(String writerNickname, boolean isRead, Member receiver, Solution solution,
                                        LocalDateTime createdAt) {
        return Notification.builder()
                .type(this.type)
                .writerNickname(writerNickname)
                .isRead(isRead)
                .receiver(receiver)
                .solution(solution)
                .createdAt(createdAt);
    }

    public Notification 생성_안읽음(Member receiver, Solution solution, Review review) {
        return 기본_빌더_생성(review.getMember().getNickname(), false, receiver, solution, review.getCreatedAt()).review(
                review).build();
    }

    public Notification 생성_안읽음(Member receiver, Solution solution, Comment comment) {
        return 기본_빌더_생성(comment.getMember().getNickname(), false, receiver, solution, comment.getCreatedAt()).comment(
                comment).build();
    }
}
