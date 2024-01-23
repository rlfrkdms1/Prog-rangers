package com.prograngers.backend.repository.notification;

import com.prograngers.backend.dto.notification.response.NotificationResponse;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;

@Repository
public class CachedEventRepository {

    private Map<String, NotificationResponse> cachedEvents = new ConcurrentHashMap<>();

    public void save(String id, NotificationResponse data) {
        cachedEvents.put(id, data);
    }

    public Map<String, NotificationResponse> findAllByMemberId(String memberId) {
        return cachedEvents.entrySet().stream()
                .filter(entry -> entry.getKey().startsWith(memberId))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

}
