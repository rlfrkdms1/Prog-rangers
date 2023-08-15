package com.prograngers.backend.service.auth;

import com.prograngers.backend.dto.response.auth.KakaoTokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OauthService {

    private final WebClient webClient;

    public KakaoTokenResponse kakaoGetToken(String code) {
        return webClient.post()
                .uri("https://kauth.kakao.com/oauth/token")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .bodyValue(convertMap(code))
                .retrieve()
                .bodyToMono(KakaoTokenResponse.class)
                .block();
    }

    public MultiValueMap<String, String> convertMap(String code){
        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("grant_type", "authorization_code");
        requestBody.add("client_id", "d7d727c879817f72882dbf6b12c1a268");
        requestBody.add("redirect_uri", "http://localhost:8080/oauth/kakao/redirect");
        requestBody.add("code", code);
        requestBody.add("client_secret","LWZFQLAPkTZYewh18oxf6zVAscgpFObR");
        return requestBody;
    }
}
