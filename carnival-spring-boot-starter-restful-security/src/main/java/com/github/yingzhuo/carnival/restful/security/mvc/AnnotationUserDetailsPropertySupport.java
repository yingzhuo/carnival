/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.restful.security.mvc;

import com.github.yingzhuo.carnival.restful.security.annotation.UserDetailsProperty;
import com.github.yingzhuo.carnival.restful.security.core.RestfulSecurityContext;
import lombok.val;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Objects;

/**
 * @author 应卓
 * @since 1.6.25
 */
public class AnnotationUserDetailsPropertySupport implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(UserDetailsProperty.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        val property = getPropertyExp(parameter);
        val ignoreExceptions = getIgnoreExceptions(parameter);
        val userDetails = RestfulSecurityContext.getUserDetails().orElse(null);

        if (property.isEmpty()) {
            return userDetails;
        }

        if (userDetails != null) {
            try {
                return new BeanWrapperImpl(userDetails).getPropertyValue(property);
            } catch (Throwable e) {
                if (ignoreExceptions)
                    return null;
                else
                    throw e;
            }
        }
        return null;
    }

    private String getPropertyExp(MethodParameter parameter) {
        val annotation = parameter.getParameterAnnotation(UserDetailsProperty.class);
        Objects.requireNonNull(annotation); //  double check (其实不太有必要)
        return annotation.value();
    }

    private boolean getIgnoreExceptions(MethodParameter parameter) {
        val annotation = parameter.getParameterAnnotation(UserDetailsProperty.class);
        Objects.requireNonNull(annotation); //  double check (其实不太有必要)
        return annotation.ignoreExceptions();
    }

}
