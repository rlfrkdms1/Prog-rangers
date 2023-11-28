package com.prograngers.backend.dto.dashboard.response;

import static com.prograngers.backend.entity.NotificationType.COMMENT;
import static com.prograngers.backend.entity.NotificationType.REVIEW;

import com.prograngers.backend.entity.Notification;
import com.prograngers.backend.entity.solution.Solution;
import com.prograngers.backend.exception.badrequest.InvalidNotificationTypeException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class NotificationWithSolutionResponse {

    private String type;
    private String nickname;
    private String solutionTitle;
    private String content;
    private Long solutionId;
    private boolean isRead;

    public static NotificationWithSolutionResponse of(Notification notification, Solution solution) {
        return NotificationWithSolutionResponse.builder()
                .type(notification.getType().name())
                .nickname(notification.getWriterNickname())
                .solutionTitle(solution.getTitle())
                .solutionId(solution.getId())
                .content(getContent(notification))
                .isRead(notification.isRead())
                .build();
    }

    public static NotificationWithSolutionResponse from(Notification notification) {
        Solution solution = notification.getSolution();
        return NotificationWithSolutionResponse.builder()
                .type(notification.getType().name())
                .nickname(notification.getWriterNickname())
                .solutionTitle(solution.getTitle())
                .solutionId(solution.getId())
                .content(getContent(notification))
                .build();
    }

    private static String getContent(Notification notification) {
        if (notification.getType() == REVIEW) {
            return notification.getReview().getContent();
        }
        if (notification.getType() == COMMENT) {
            return notification.getComment().getContent();
        }
        throw new InvalidNotificationTypeException();
    }
}
