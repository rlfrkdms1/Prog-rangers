package com.prograngers.backend.dto.notification.response;

import com.prograngers.backend.dto.dashboard.response.NotificationWithSolutionResponse;
import com.prograngers.backend.entity.Notification;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Slice;

@Getter
@AllArgsConstructor
@Builder
public class ShowNotificationsResponse {

    private boolean hasNext;
    private List<NotificationWithSolutionResponse> notifications;

    public static ShowNotificationsResponse from(Slice<Notification> notifications) {
        return ShowNotificationsResponse.builder()
                .hasNext(notifications.hasNext())
                .notifications(notifications.getContent().stream().map(NotificationWithSolutionResponse::from)
                        .collect(Collectors.toList()))
                .build();
    }

}
