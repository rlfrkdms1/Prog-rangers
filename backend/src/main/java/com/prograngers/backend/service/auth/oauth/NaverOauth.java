package com.prograngers.backend.service.auth.oauth;

import static com.prograngers.backend.service.auth.OauthConstant.BEARER_FORMAT;

import com.prograngers.backend.dto.auth.response.naver.GetNaverTokenResponse;
import com.prograngers.backend.dto.auth.response.naver.GetNaverUserInfoResponse;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class NaverOauth {

    private final static String TOKEN_URI = "https://nid.naver.com/oauth2.0/token";
    private final static String USER_INFO_URI = "https://openapi.naver.com/v1/nid/me";
    private final static String state = "test_code";
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

    public GetNaverTokenResponse getNaverToken(String code, String state) {
        String encodedState = URLEncoder.encode(state, StandardCharsets.UTF_8);
        return webClient.get()
                .uri(TOKEN_URI + "?client_id=" + clientId + "&grant_type="
                        + grantType + "&client_secret=" + clientSecret + "&state=" + encodedState
                        + "&code=" + code)
                .retrieve()
                .bodyToMono(GetNaverTokenResponse.class)
                .block();
    }

    public GetNaverUserInfoResponse getUserInfo(String accessToken) {
        return webClient.get()
                .uri(USER_INFO_URI)
                .header(HttpHeaders.AUTHORIZATION, String.format(BEARER_FORMAT, accessToken))
                .retrieve()
                .bodyToMono(GetNaverUserInfoResponse.class)
                .block();
    }

    public String getState() {
        return state;
    }

}
