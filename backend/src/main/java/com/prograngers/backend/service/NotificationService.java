package com.prograngers.backend.service;

import com.prograngers.backend.dto.response.notification.NotificationResponse;
import com.prograngers.backend.entity.Notification;
import com.prograngers.backend.entity.Review;
import com.prograngers.backend.entity.comment.Comment;
import com.prograngers.backend.entity.member.Member;
import com.prograngers.backend.entity.solution.Solution;
import com.prograngers.backend.exception.ServerSentEventConnectException;
import com.prograngers.backend.repository.CachedEventRepository;
import com.prograngers.backend.repository.NotificationRepository;
import com.prograngers.backend.repository.SseEmitterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;

import static com.prograngers.backend.entity.NotificationType.COMMENT;
import static com.prograngers.backend.entity.NotificationType.REVIEW;
@Transactional
@Service
@RequiredArgsConstructor
public class NotificationService {

    private static final String REVIEW_NOTIFICATION_FORMAT = "@%s님이 %s에 리뷰를 남겼습니다.";
    private static final String COMMENT_NOTIFICATION_FORMAT = "@%s님이 %s에 댓글을 남겼습니다.";
    private final NotificationRepository notificationRepository;
    private final SseEmitterRepository sseEmitterRepository;
    private final CachedEventRepository cachedEventRepository;


    private void sendToClient(SseEmitter emitter, String emitterId, Object data) {
        try {
            emitter.send(SseEmitter.event()
                    .id(emitterId)
                    .name("notification")
                    .data(data));
        } catch (IOException exception) {
            sseEmitterRepository.deleteById(emitterId);
            throw new ServerSentEventConnectException();
        }
    }

    public SseEmitter subscribe(Long memberId, String lastEventId) {
        String emitterId = memberId + "_" + lastEventId;
        SseEmitter emitter = sseEmitterRepository.save(emitterId, new SseEmitter());

        emitter.onCompletion(() -> sseEmitterRepository.deleteById(emitterId));
        emitter.onTimeout(() -> sseEmitterRepository.deleteById(emitterId));

        sendToClient(emitter, emitterId, "Notification Subscribe Success");

        if (!lastEventId.isEmpty()) {
            Map<String, NotificationResponse> cachedEvents = cachedEventRepository.findAllByMemberId(String.valueOf(memberId));
            cachedEvents.entrySet().stream()
                    .filter(entry -> entry.getKey().compareTo(lastEventId) > 0)
                    .forEach(entry -> sendToClient(emitter, emitterId, entry.getValue()));
        }
        return emitter;
    }

}
