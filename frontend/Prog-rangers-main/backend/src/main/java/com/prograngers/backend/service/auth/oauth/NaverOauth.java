package com.prograngers.backend.service.auth.oauth;

import com.prograngers.backend.dto.response.auth.naver.NaverTokenResponse;
import com.prograngers.backend.dto.response.auth.naver.NaverUserInfoResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.URI;

import static com.prograngers.backend.service.auth.OauthConstant.BEARER_FORMAT;

@Component
public class NaverOauth {

    private final static String TOKEN_URI = "https://nid.naver.com/oauth2.0/token";
    private final static String USER_INFO_URI = "https://openapi.naver.com/v1/nid/me";
    private final static String code = "test_code";
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

    public NaverTokenResponse getNaverToken(String code, String state) {
        return webClient.get()
                .uri(TOKEN_URI + "?client_id=" + clientId + "&grant_type="
                        + grantType + "&client_secret=" + clientSecret + "&state=" + state
                        + "&code=" + code)
                .retrieve()
                .bodyToMono(NaverTokenResponse.class)
                .block();
    }

    public NaverUserInfoResponse getUserInfo(String accessToken) {
        return webClient.get()
                .uri(USER_INFO_URI)
                .header(HttpHeaders.AUTHORIZATION, String.format(BEARER_FORMAT, accessToken))
                .retrieve()
                .bodyToMono(NaverUserInfoResponse.class)
                .block();
    }

    public String getCode() {
        return code;
    }

}
