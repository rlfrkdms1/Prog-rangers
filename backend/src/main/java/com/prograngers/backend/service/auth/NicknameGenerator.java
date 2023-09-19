package com.prograngers.backend.service.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
public class NicknameGenerator {

    private final WebClient webClient;
    private static final String NICKNAME_GENERATOR_URI = "https://nickname.hwanmoo.kr/?format=json";

    public GenerateNicknameResponse getRandomNickname() {
        return webClient.get()
                .uri(NICKNAME_GENERATOR_URI)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .retrieve()
                .bodyToMono(GenerateNicknameResponse.class)
                .block();
    }

}
