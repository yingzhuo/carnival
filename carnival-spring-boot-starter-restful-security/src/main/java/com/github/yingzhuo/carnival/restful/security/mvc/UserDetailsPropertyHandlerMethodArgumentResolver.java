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
import com.github.yingzhuo.carnival.restful.security.token.StringToken;
import com.github.yingzhuo.carnival.restful.security.token.Token;
import com.github.yingzhuo.carnival.restful.security.token.UsernamePasswordToken;
import com.github.yingzhuo.carnival.restful.security.userdetails.UserDetails;
import lombok.val;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * @author 应卓
 */
public class UserDetailsPropertyHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(UserDetailsProperty.class) ||
                parameter.getParameterType() == UsernamePasswordToken.class ||
                parameter.getParameterType() == Token.class ||
                parameter.getParameterType() == StringToken.class ||
                parameter.getParameterType() == UserDetails.class;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {

        if (parameter.getParameterType() == Token.class ||
                parameter.getParameterType() == UsernamePasswordToken.class ||
                parameter.getParameterType() == StringToken.class)
        {
            return RestfulSecurityContext.getToken().orElse(null);
        }

        if (parameter.getParameterType() == UserDetails.class) {
            return RestfulSecurityContext.getUserDetails().orElse(null);
        }

        val property = parameter.getParameterAnnotation(UserDetailsProperty.class).value();
        val ignoreExceptions = parameter.getParameterAnnotation(UserDetailsProperty.class).ignoreExceptions();
        val userDetails = RestfulSecurityContext.getUserDetails().orElse(null);

        if (StringUtils.isBlank(property)) {
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
}
