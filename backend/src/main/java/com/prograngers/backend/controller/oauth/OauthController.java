package com.prograngers.backend.controller.oauth;

import com.prograngers.backend.dto.response.auth.KakaoTokenResponse;
import com.prograngers.backend.service.auth.OauthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class OauthController {

    private final OauthService oauthService;

    @GetMapping("/oauth/kakao/redirect")
    public void kakaoLogin(@RequestParam String code) {
        log.info("code = " + code );
        KakaoTokenResponse kakaoTokenResponse = oauthService.kakaoGetToken(code);
        log.info(kakaoTokenResponse.getAccess_token());
    }
}
