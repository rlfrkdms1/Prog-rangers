package com.prograngers.backend.service.auth.oauth;

import com.prograngers.backend.dto.auth.response.kakao.GetKakaoTokenResponse;
import com.prograngers.backend.dto.auth.response.kakao.GetKakaoUserInfoResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import static com.prograngers.backend.service.auth.oauth.MultiValueMapConverter.convertToMultiValueMap;
import static com.prograngers.backend.service.auth.OauthConstant.BEARER_FORMAT;

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

    public GetKakaoTokenResponse kakaoGetToken(String code) {
        return webClient.post()
                .uri(TOKEN_URI)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .bodyValue(convertToMultiValueMap(grantType, clientId, redirectUri, code, clientSecret))
                .retrieve()
<<<<<<< HEAD
                .bodyToMono(KakaoTokenResponse.class)
=======
                .bodyToMono(GetKakaoTokenResponse.class)
                .doOnError(e -> {
                    log.info("Error occurred: ", e);
                })
>>>>>>> 1e68fe7e332e0378fce8667f04f28cff021631e1
                .block();
    }

    public GetKakaoUserInfoResponse kakaoGetUserInfo(String accessToken){
        return webClient.get()
                .uri(USER_INFO_URI)
                .header(HttpHeaders.AUTHORIZATION, String.format(BEARER_FORMAT, accessToken))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .retrieve()
                .bodyToMono(GetKakaoUserInfoResponse.class)
                .block();
    }
}
