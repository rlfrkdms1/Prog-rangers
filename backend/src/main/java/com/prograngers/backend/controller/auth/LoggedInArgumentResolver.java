package com.prograngers.backend.controller.auth;

import com.prograngers.backend.exception.unauthorization.NotExistAccessTokenException;
import com.prograngers.backend.service.auth.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
@RequiredArgsConstructor
public class LoggedInArgumentResolver implements HandlerMethodArgumentResolver {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(LoggedInMember.class)
                && Long.class.isAssignableFrom(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        String headerAuthorization = webRequest.getHeader(HttpHeaders.AUTHORIZATION);
        LoggedInMember loggedInMember = parameter.getParameterAnnotation(LoggedInMember.class);
        if (!loggedInMember.required() && headerAuthorization == null) {
            return null;
        }
        validExistAccessTokenInHeader(headerAuthorization);
        String accessToken = headerAuthorization.split(" ")[1];
        return jwtTokenProvider.getMemberId(accessToken);
    }

    private void validExistAccessTokenInHeader(String headerAuthorization) {
        if (headerAuthorization == null) {
            throw new NotExistAccessTokenException();
        }
    }

}
