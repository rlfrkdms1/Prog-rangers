package com.prograngers.backend.config;

import com.prograngers.backend.controller.auth.AuthInterceptor;
import com.prograngers.backend.controller.auth.LoggedInArgumentResolver;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final AuthInterceptor authInterceptor;
    private final LoggedInArgumentResolver loggedInArgumentResolver;
    private static final String FRONTEND_DOMAIN = "http://ec2-13-124-131-171.ap-northeast-2.compute.amazonaws.com:3000";
    private static final String FRONT_LOCALHOST = "http://localhost:3000";
    private static final String CORS_ALLOWED_METHODS = "GET,POST,HEAD,PUT,PATCH,DELETE,TRACE,OPTIONS";


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(loggedInArgumentResolver);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedMethods(CORS_ALLOWED_METHODS.split(","))
                .allowedOrigins(FRONTEND_DOMAIN, FRONT_LOCALHOST)
                .exposedHeaders(HttpHeaders.SET_COOKIE, HttpHeaders.LOCATION)
                .allowCredentials(true);
    }
}
