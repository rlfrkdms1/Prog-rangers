package com.prograngers.backend.dto.dashboard.response;

import com.prograngers.backend.entity.Notification;
import com.prograngers.backend.entity.solution.Solution;
import com.prograngers.backend.exception.badrequest.InvalidNotificationTypeException;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.prograngers.backend.entity.NotificationType.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NotificationWithSolutionResponse {

    private String type;
    private String nickname;
    private String solutionTitle;
    private String content;
    private Long solutionId;

    @Builder
    public NotificationWithSolutionResponse(String type, String nickname, String solutionTitle, String content, Long solutionId) {
        this.type = type;
        this.nickname = nickname;
        this.solutionTitle = solutionTitle;
        this.content = content;
        this.solutionId = solutionId;
    }

    public static NotificationWithSolutionResponse of(Notification notification, Solution solution) {
        return NotificationWithSolutionResponse.builder()
                .type(notification.getType().name())
                .nickname(notification.getWriterNickname())
                .solutionTitle(solution.getTitle())
                .solutionId(solution.getId())
                .content(getContent(notification))
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

    private static String getContent(Notification notification){
        if(notification.getType() == REVIEW) return notification.getReview().getContent();
        if(notification.getType() == COMMENT) return notification.getComment().getContent();
        throw new InvalidNotificationTypeException();
    }
}
