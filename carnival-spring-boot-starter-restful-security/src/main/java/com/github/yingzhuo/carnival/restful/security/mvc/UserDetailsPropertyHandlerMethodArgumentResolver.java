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

/**
 * @author 应卓
 */
public class UserDetailsPropertyHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(UserDetailsProperty.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        val property = parameter.getParameterAnnotation(UserDetailsProperty.class).value();
        val userDetails = RestfulSecurityContext.getUserDetails().orElse(null);

        if ("".equals(property)) {
            return userDetails;
        } else {
            if (userDetails != null) {

                try {
                    return new BeanWrapperImpl(userDetails).getPropertyValue(property);
                } catch (Throwable e) {
                    return null;
                }

            } else {
                return null;
            }
        }
    }

}
