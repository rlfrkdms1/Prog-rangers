package com.prograngers.backend.service.auth;

import com.prograngers.backend.dto.response.auth.KakaoTokenResponse;
import com.prograngers.backend.dto.response.auth.KakaoUserInfoResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

import static com.prograngers.backend.service.auth.OauthConstant.BEARER_FORMAT;
import static com.prograngers.backend.service.auth.OauthConstant.CLIENT_ID;
import static com.prograngers.backend.service.auth.OauthConstant.CLIENT_SECRET;
import static com.prograngers.backend.service.auth.OauthConstant.CODE;
import static com.prograngers.backend.service.auth.OauthConstant.GRANT_TYPE;
import static com.prograngers.backend.service.auth.OauthConstant.REDIRECT_URI;

@Component
public class KakaoOauth {

    private final static String TOKEN_URI = "https://kauth.kakao.com/oauth/token";
    private final static String USER_INFO_URI = "https://kapi.kakao.com/v2/user/me";
    private final String grantType;
    private final String clientId;
    private final String redirectUri;
    private final String clientSecret;
    private final WebClient webClient;

    public KakaoOauth(@Value("${kakao.grant.type}") String grantType,
                      @Value("${kakao.client.id}") String clientId,
                      @Value("${kakao.redirect.uri}") String redirectUri,
                      @Value("${kakao.client.secret}") String clientSecret,
                      WebClient webClient) {
        this.grantType = grantType;
        this.clientId = clientId;
        this.redirectUri = redirectUri;
        this.clientSecret = clientSecret;
        this.webClient = webClient;
    }

    public KakaoTokenResponse kakaoGetToken(String code) {
        return webClient.post()
                .uri(TOKEN_URI)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .bodyValue(convertToMultiValueMap(code))
                .retrieve()
                .bodyToMono(KakaoTokenResponse.class)
                .block();
    }

    public KakaoUserInfoResponse kakaoGetUserInfo(String accessToken){
        return webClient.get()
                .uri(USER_INFO_URI)
                .header(HttpHeaders.AUTHORIZATION, String.format(BEARER_FORMAT, accessToken))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .retrieve()
                .bodyToMono(KakaoUserInfoResponse.class)
                .block();
    }

    public MultiValueMap<String, String> convertToMultiValueMap(String code){
        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add(GRANT_TYPE, grantType);
        requestBody.add(CLIENT_ID, clientId);
        requestBody.add(REDIRECT_URI, redirectUri);
        requestBody.add(CODE, code);
        requestBody.add(CLIENT_SECRET, clientSecret);
        return requestBody;
    }

}
