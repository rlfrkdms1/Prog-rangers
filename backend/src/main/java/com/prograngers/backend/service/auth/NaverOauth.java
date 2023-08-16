package com.prograngers.backend.service.auth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class NaverOauth {

    private final static String TOKEN_URI = "https://nid.naver.com/oauth2.0/token";
    private final static String USER_INFO_URI = "https://openapi.naver.com/v1/nid/me";
    private final String grantType;
    private final String clientId;
    private final String redirectUri;
    private final String clientSecret;
    private final WebClient webClient;

    public NaverOauth(@Value("${naver.grant.type}") String grantType,
                      @Value("${naver.client.id}") String clientId,
                      @Value("${naver.redirect.uri}") String redirectUri,
                      @Value("${naver.client.secret}") String clientSecret,
                      WebClient webClient) {
        this.grantType = grantType;
        this.clientId = clientId;
        this.redirectUri = redirectUri;
        this.clientSecret = clientSecret;
        this.webClient = webClient;
    }

}
