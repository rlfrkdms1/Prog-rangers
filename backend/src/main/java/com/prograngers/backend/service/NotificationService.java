package com.prograngers.backend.service;

import static com.prograngers.backend.entity.NotificationType.COMMENT;
import static com.prograngers.backend.entity.NotificationType.REVIEW;
import static com.prograngers.backend.service.DashBoardService.DASHBOARD_NOTIFICATION_LIMIT;

import com.prograngers.backend.dto.notification.response.ShowNotificationResponse;
import com.prograngers.backend.dto.notification.response.ShowNotificationsResponse;
import com.prograngers.backend.entity.Notification;
import com.prograngers.backend.entity.comment.Comment;
import com.prograngers.backend.entity.member.Member;
import com.prograngers.backend.entity.review.Review;
import com.prograngers.backend.entity.solution.Solution;
import com.prograngers.backend.exception.ServerSentEventConnectException;
import com.prograngers.backend.exception.badrequest.InvalidPageNumberException;
import com.prograngers.backend.exception.badrequest.InvalidPageSizeException;
import com.prograngers.backend.repository.notification.CachedEventRepository;
import com.prograngers.backend.repository.notification.NotificationRepository;
import com.prograngers.backend.repository.notification.SseEmitterRepository;
import java.io.IOException;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Transactional
@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final SseEmitterRepository sseEmitterRepository;
    private final CachedEventRepository cachedEventRepository;

    /**
     * @ddongguri-bing님이 풀이1에 리뷰를 남겼습니다. "이 부분은 ~ 로 고칠 수 있을 것 같아요 ?"
     */
    public void send(Review review, Solution solution, Member writer) {
        Notification notification = createReviewNotification(review, solution, writer);
        ShowNotificationResponse showNotificationResponse = ShowNotificationResponse.from(notification);
        sseEmitterRepository.findAllByMemberId(String.valueOf(notification.getReceiver().getId()))
                .forEach((emitterId, emitter) -> {
                    cachedEventRepository.save(emitterId, showNotificationResponse);
                    sendToClient(emitter, emitterId, showNotificationResponse);
                });
        notificationRepository.save(notification);
    }

    private Notification createReviewNotification(Review review, Solution solution, Member writer) {
        Notification notification = Notification.builder()
                .type(REVIEW)
                .writerNickname(writer.getNickname())
                .review(review)
                .solution(solution)
                .isRead(false)
                .receiver(solution.getMember())
                .build();
        return notification;
    }

    public void send(Comment comment, Solution solution, Member writer) {
        Notification notification = createCommentNotification(comment, solution, writer);
        ShowNotificationResponse showNotificationResponse = ShowNotificationResponse.from(notification);
        sseEmitterRepository.findAllByMemberId(String.valueOf(notification.getReceiver().getId()))
                .forEach((emitterId, emitter) -> {
                    cachedEventRepository.save(emitterId, showNotificationResponse);
                    sendToClient(emitter, emitterId, showNotificationResponse);
                });
        notificationRepository.save(notification);
    }


    private Notification createCommentNotification(Comment comment, Solution solution, Member writer) {
        Notification notification = Notification.builder()
                .type(COMMENT)
                .writerNickname(writer.getNickname())
                .comment(comment)
                .solution(solution)
                .isRead(false)
                .receiver(solution.getMember())
                .build();
        return notification;
    }


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
            Map<String, ShowNotificationResponse> cachedEvents = cachedEventRepository.findAllByMemberId(
                    String.valueOf(memberId));
            cachedEvents.entrySet().stream()
                    .filter(entry -> entry.getKey().compareTo(lastEventId) > 0)
                    .forEach(entry -> sendToClient(emitter, emitterId, entry.getValue()));
        }
        return emitter;
    }

    public ShowNotificationsResponse getNotifications(Long memberId, Pageable pageable) {
        validPageable(pageable);
        Slice<Notification> notifications = notificationRepository.findPageByMemberId(memberId, pageable);
        ShowNotificationsResponse response = ShowNotificationsResponse.from(notifications);
        notifications.stream().forEach(Notification::read);
        return response;
    }

    private void validPageable(Pageable pageable) {
        validPageNumber(pageable);
        validPageSize(pageable);
    }

    private void validPageNumber(Pageable pageable) {
        if (pageable.getPageNumber() < 2) {
            throw new InvalidPageNumberException();
        }
    }

    private void validPageSize(Pageable pageable) {
        if (pageable.getPageSize() < 1) {
            throw new InvalidPageSizeException();
        }
    }

}
