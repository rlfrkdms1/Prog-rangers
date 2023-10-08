package com.prograngers.backend.controller;

import com.prograngers.backend.controller.auth.LoggedInMember;
import com.prograngers.backend.dto.notification.response.ShowNotificationsResponse;
import com.prograngers.backend.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/notifications")
public class NotificationController {

    private final NotificationService notificationService;
    private static final String DASHBOARD_NOTIFICATION_DEFAULT_PAGE_NUMBER = "2";

    @GetMapping(value = "/subscribe", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter subscribe(
            @LoggedInMember Long memberId,
            @RequestHeader(value = "Last-Event-ID", required = false, defaultValue = "") String lastEventId) {
        return notificationService.subscribe(memberId, lastEventId);
    }

    @GetMapping
    public ShowNotificationsResponse show(@LoggedInMember Long memberId,
                                          @RequestParam(defaultValue = DASHBOARD_NOTIFICATION_DEFAULT_PAGE_NUMBER) int page){
        return notificationService.getNotifications(memberId, page);
    }
}
