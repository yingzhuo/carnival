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

import com.github.yingzhuo.carnival.restful.security.SkipReason;
import com.github.yingzhuo.carnival.restful.security.annotation.StringTokenValue;
import com.github.yingzhuo.carnival.restful.security.annotation.UserDetailsProperty;
import com.github.yingzhuo.carnival.restful.security.core.RestfulSecurityContext;
import com.github.yingzhuo.carnival.restful.security.token.BytesToken;
import com.github.yingzhuo.carnival.restful.security.token.StringToken;
import com.github.yingzhuo.carnival.restful.security.token.Token;
import com.github.yingzhuo.carnival.restful.security.token.UsernamePasswordToken;
import com.github.yingzhuo.carnival.restful.security.userdetails.UserDetails;
import lombok.val;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * @author 应卓
 */
public class RestfulSecurityHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(UserDetailsProperty.class) ||
                (parameter.hasParameterAnnotation(StringTokenValue.class) && parameter.getParameterType() == String.class) ||
                parameter.getParameterType() == UserDetails.class ||
                parameter.getParameterType() == Token.class ||
                parameter.getParameterType() == StringToken.class ||
                parameter.getParameterType() == UsernamePasswordToken.class ||
                parameter.getParameterType() == BytesToken.class ||
                parameter.getParameterType() == SkipReason.class;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {

        if (parameter.getParameterType() == UserDetails.class) {
            return RestfulSecurityContext.getUserDetails().orElse(null);
        }

        if (parameter.getParameterType() == Token.class) {
            return RestfulSecurityContext.getToken().orElse(null);
        }

        if (parameter.getParameterType() == StringToken.class) {
            return (StringToken) RestfulSecurityContext.getToken().orElse(null);
        }

        if (parameter.getParameterType() == UsernamePasswordToken.class) {
            return (UsernamePasswordToken) RestfulSecurityContext.getToken().orElse(null);
        }

        if (parameter.getParameterType() == BytesToken.class) {
            return (BytesToken) RestfulSecurityContext.getToken().orElse(null);
        }

        if (parameter.getParameterType() == SkipReason.class) {
            return RestfulSecurityContext.getSkipReason().orElse(null);
        }

        if (parameter.hasParameterAnnotation(StringTokenValue.class) && parameter.getParameterType() == String.class) {
            final Token token = RestfulSecurityContext.getToken().orElse(null);
            if (token == null) {
                return null;
            } else {
                return ((StringToken) token).getValue();
            }
        }

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
        if (annotation == null) {
            throw new NullPointerException();
        }
        return annotation.value();
    }

    private boolean getIgnoreExceptions(MethodParameter parameter) {
        val annotation = parameter.getParameterAnnotation(UserDetailsProperty.class);
        if (annotation == null) {
            throw new NullPointerException();
        }
        return annotation.ignoreExceptions();
    }

}
