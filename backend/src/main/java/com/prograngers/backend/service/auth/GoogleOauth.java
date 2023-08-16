package com.prograngers.backend.service.auth;

import com.prograngers.backend.dto.response.auth.GoogleTokenResponse;
import com.prograngers.backend.dto.response.auth.KakaoTokenResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import static com.prograngers.backend.service.auth.MultiValueMapConverter.convertToMultiValueMap;

@Component
public class GoogleOauth {

    private final static String TOKEN_URI = "https://oauth2.googleapis.com/token";
    private final String grantType;
    private final String clientId;
    private final String redirectUri;
    private final String clientSecret;
    private final WebClient webClient;

    public GoogleOauth(@Value("${google.grant.type}") String grantType,
                      @Value("${google.client.id}") String clientId,
                      @Value("${google.redirect.uri}") String redirectUri,
                      @Value("${google.client.secret}") String clientSecret,
                      WebClient webClient) {
        this.grantType = grantType;
        this.clientId = clientId;
        this.redirectUri = redirectUri;
        this.clientSecret = clientSecret;
        this.webClient = webClient;
    }

    public GoogleTokenResponse googleGetToken(String code) {
        return webClient.post()
                .uri(TOKEN_URI)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .bodyValue(convertToMultiValueMap(grantType, clientId, redirectUri, code, clientSecret))
                .retrieve()
                .bodyToMono(GoogleTokenResponse.class)
                .block();
    }
}
