package com.prograngers.backend.dto.response.dashboard;

import com.prograngers.backend.controller.NotificationController;
import com.prograngers.backend.entity.Notification;
import com.prograngers.backend.entity.NotificationType;
import com.prograngers.backend.entity.member.Member;
import com.prograngers.backend.entity.solution.Solution;
import com.prograngers.backend.exception.badrequest.InvalidNotificationTypeException;
import lombok.Builder;
import lombok.Getter;

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
        if(notification.getType() == NotificationType.REVIEW) return notification.getReview().getContent();
        if(notification.getType() == NotificationType.COMMENT) return notification.getComment().getContent();
        throw new InvalidNotificationTypeException();
    }
}
