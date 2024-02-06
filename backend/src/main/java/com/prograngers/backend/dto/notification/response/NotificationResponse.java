package com.prograngers.backend.dto.notification.response;

import static com.prograngers.backend.entity.NotificationType.COMMENT;
import static com.prograngers.backend.entity.NotificationType.REVIEW;

import com.prograngers.backend.entity.Notification;
import com.prograngers.backend.exception.badrequest.invalidvalue.InvalidNotificationTypeException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class NotificationResponse {

    private String nickname;
    private String content;
    private Long solutionId;

    public static NotificationResponse from(Notification notification) {
        return NotificationResponse.builder()
                .nickname(notification.getWriterNickname())
                .content(getContent(notification))
                .solutionId(notification.getSolution().getId())
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
