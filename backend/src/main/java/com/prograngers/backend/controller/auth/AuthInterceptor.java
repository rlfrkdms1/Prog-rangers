package com.prograngers.backend.controller.auth;

import com.prograngers.backend.exception.unauthorization.InvalidAccessTokenException;
import com.prograngers.backend.exception.unauthorization.NotExistAccessTokenException;
import com.prograngers.backend.service.auth.JwtTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthInterceptor implements HandlerInterceptor {

    private final JwtTokenProvider jwtTokenProvider;
    private static final String AUTHORIZATION_HEADER_PREFIX = "Bearer ";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;

        Login loginAnnotation = handlerMethod.getMethodAnnotation(Login.class);
        if (loginAnnotation == null) {
            return true;
        }

        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (header != null) {
            validHeader(header);
            validAccessToken(header);
            return true;
        }

        validLoginRequired(loginAnnotation);

        return true;
    }

    private void validHeader(String header) {
        if (!header.startsWith(AUTHORIZATION_HEADER_PREFIX)) {
            throw new InvalidAccessTokenException();
        }
    }

    private void validAccessToken(String header) {
        String accessToken = header.replaceFirst(AUTHORIZATION_HEADER_PREFIX, "");
        jwtTokenProvider.validToken(accessToken);
    }

    private void validLoginRequired(Login loginAnnotation) {
        if (loginAnnotation.required()) {
            throw new NotExistAccessTokenException(); // required true인데 토큰이 없는 경우
        }
    }


}
