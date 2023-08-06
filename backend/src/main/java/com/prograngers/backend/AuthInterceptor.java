package com.prograngers.backend;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class AuthInterceptor implements HandlerInterceptor {
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

        if(request.getHeader(HttpHeaders.AUTHORIZATION) != null) {
            //token 유효성 검사 (유효 기간)
        }

        if(!loginAnnotation.required()) return true; // required true면 에러 던져야함

        return true;
    }


    public AuthInterceptor() {
        super();
    }



}
