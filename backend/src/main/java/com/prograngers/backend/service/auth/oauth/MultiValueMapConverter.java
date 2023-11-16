package com.prograngers.backend.service.auth.oauth;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public class MultiValueMapConverter {

    private static final String GRANT_TYPE = "grant_type";
    private static final String CLIENT_ID = "client_id";
    private static final String REDIRECT_URI = "redirect_uri";
    private static final String CODE = "code";
    private static final String CLIENT_SECRET = "client_secret";

    public static MultiValueMap<String, String> convertToMultiValueMap(String grantType, String clientId,
                                                                       String redirectUri, String code,
                                                                       String clientSecret) {
        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add(GRANT_TYPE, grantType);
        requestBody.add(CLIENT_ID, clientId);
        requestBody.add(REDIRECT_URI, redirectUri);
        requestBody.add(CODE, code);
        requestBody.add(CLIENT_SECRET, clientSecret);
        return requestBody;
    }
}
