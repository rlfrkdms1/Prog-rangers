package com.prograngers.backend.repository.notification;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Repository
public class SseEmitterRepository {

    private Map<String, SseEmitter> emitters = new ConcurrentHashMap<>();

    public SseEmitter save(String id, SseEmitter emitter) {
        emitters.put(id, emitter);
        return emitter;
    }

    public Map<String, SseEmitter> findAllByMemberId(String memberId) {
        return emitters.entrySet().stream()
                .filter(entry -> entry.getKey().startsWith(memberId))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public void deleteById(String id) {
        emitters.remove(id);
    }


}
