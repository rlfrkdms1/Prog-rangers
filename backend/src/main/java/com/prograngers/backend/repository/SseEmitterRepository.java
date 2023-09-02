package com.prograngers.backend.repository;

import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
public class SseEmitterRepository {

    private Map<String, SseEmitter> emitters = new ConcurrentHashMap<>();

    public SseEmitter save(String id, SseEmitter emitter) {
        emitters.put(id, emitter);
        return emitter;
    }

}
