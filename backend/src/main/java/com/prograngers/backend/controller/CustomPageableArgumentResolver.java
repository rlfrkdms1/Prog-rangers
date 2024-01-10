package com.prograngers.backend.controller;

import com.prograngers.backend.exception.badrequest.InvalidPageNumberException;
import org.springframework.core.MethodParameter;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;
import java.util.regex.Pattern;

public class CustomPageableArgumentResolver extends PageableHandlerMethodArgumentResolver {

    private static final Pattern NUMBER_PATTERN = Pattern.compile("^[0-9]*$");

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return super.supportsParameter(parameter);
    }

    @Override
    public Pageable resolveArgument(MethodParameter methodParameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        String page = webRequest.getParameter(getParameterNameToUse(getPageParameterName(), methodParameter));
        validatePage(page);
        return super.resolveArgument(methodParameter, mavContainer, webRequest, binderFactory);
    }

    private void validatePage(String page) {
        if (page == null) {
            return;
        }
        if (!NUMBER_PATTERN.matcher(page).matches()) {
            throw new InvalidPageNumberException();
        }
    }

}
