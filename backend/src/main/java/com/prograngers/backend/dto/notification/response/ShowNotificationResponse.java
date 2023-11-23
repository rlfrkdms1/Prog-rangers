package com.prograngers.backend.dto.notification.response;

import com.prograngers.backend.entity.Notification;
import com.prograngers.backend.exception.badrequest.invalidvalue.InvalidNotificationTypeException;
import lombok.Builder;
import lombok.Getter;

import static com.prograngers.backend.entity.NotificationType.*;

@Getter
@Builder
public class ShowNotificationResponse {

    private String nickname;
    private String content;
    private Long solutionId;

    public static ShowNotificationResponse from(Notification notification) {
        return ShowNotificationResponse.builder()
                .nickname(notification.getWriterNickname())
                .content(getContent(notification))
                .solutionId(notification.getSolution().getId())
                .build();
    }

    private static String getContent(Notification notification) {
        if (notification.getType() == REVIEW) return notification.getReview().getContent();
        if (notification.getType() == COMMENT) return notification.getComment().getContent();
        throw new InvalidNotificationTypeException();
    }


}
