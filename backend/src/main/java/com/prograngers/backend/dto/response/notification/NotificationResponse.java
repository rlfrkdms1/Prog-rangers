package com.prograngers.backend.dto.response.notification;

import com.prograngers.backend.entity.Notification;
import com.prograngers.backend.entity.NotificationType;
import com.prograngers.backend.exception.badrequest.InvalidNotificationTypeException;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class NotificationResponse {

    private String title;
    private String content;
    private Long solutionId;

    public static NotificationResponse from(Notification notification) {
        return NotificationResponse.builder()
                .title(notification.getTitle())
                .content(getContent(notification))
                .solutionId(notification.getSolution().getId())
                .build();
    }

    private static String getContent(Notification notification) {
        if (notification.getType() == NotificationType.REVIEW) return notification.getReview().getContent();
        if (notification.getType() == NotificationType.COMMENT) return notification.getComment().getContent();
        throw new InvalidNotificationTypeException();
    }


}
