package com.prograngers.backend.controller.auth;

import com.prograngers.backend.service.auth.JwtTokenProvider;
import com.prograngers.backend.exception.unauthorization.ExpiredTokenException;
import com.prograngers.backend.exception.unauthorization.NotExistTokenException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthInterceptor implements HandlerInterceptor {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(!(handler instanceof HandlerMethod)) return true;
        /* HandlerMethod
        Encapsulates information about a handler method consisting of a method and a bean.
        Provides convenient access to method parameters, the method return value, method annotations, etc.
        The class may be created with a bean instance or with a bean name (e.g. lazy-init bean, prototype bean).
        Use createWithResolvedBean() to obtain a HandlerMethod instance with a bean instance resolved through the associated BeanFactory.*/
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Login loginAnnotation = handlerMethod.getMethodAnnotation(Login.class);
        if (loginAnnotation == null) { // login 어노테이션 없으면 통과
            return true;
        }

        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if(header != null) {
            //token 유효성 검사 (유효 기간)
            String[] authorization = header.split(" ");
            if((new Date()).after(jwtTokenProvider.getExpiredAt(authorization[1]))){
                throw new ExpiredTokenException();
            }
            return true;
        }

        if(loginAnnotation.required()) throw new NotExistTokenException(); // required true인데 토큰이 없는 경우

        return true;
    }


}
