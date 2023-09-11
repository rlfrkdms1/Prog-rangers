package com.prograngers.backend.dto.response.dashboard;

import com.prograngers.backend.entity.Notification;
import com.prograngers.backend.entity.solution.Solution;
import com.prograngers.backend.exception.badrequest.InvalidNotificationTypeException;
import lombok.Builder;
import lombok.Getter;

import static com.prograngers.backend.entity.NotificationType.*;

@Getter
@Builder
public class NotificationInfo {

    private String type;
    private String nickname;
    private String solutionTitle;
    private String content;
    private Long solutionId;

    public static NotificationInfo of(Notification notification, Solution solution) {
        return NotificationInfo.builder()
                .type(notification.getType().name())
                .nickname(notification.getWriterNickname())
                .solutionTitle(solution.getTitle())
                .solutionId(solution.getId())
                .content(getContent(notification))
                .build();
    }

    private static String getContent(Notification notification){
        if(notification.getType() == REVIEW) return notification.getReview().getContent();
        if(notification.getType() == COMMENT) return notification.getComment().getContent();
        throw new InvalidNotificationTypeException();
    }
}
