/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.mvc.support;

import com.github.yingzhuo.carnival.mvc.IpAddress;
import com.github.yingzhuo.carnival.mvc.util.IPUtils;
import lombok.val;
import org.springframework.core.MethodParameter;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * @author 应卓
 */
public class IpAddressHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean c1 = parameter.hasParameterAnnotation(IpAddress.class) && parameter.getParameterType() == String.class;
        boolean c2 = parameter.hasParameterAnnotation(IpAddress.class) && parameter.getParameterType() == Optional.class;
        return c1 || c2;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        val request = webRequest.getNativeRequest(HttpServletRequest.class);

        String ip = null;

        if (request != null) {
            ip = IPUtils.getIpAddress(request);
            if (!StringUtils.hasText(ip)) {
                ip = null;
            }
        }

        if (parameter.getParameterType() == String.class) {
            return ip;
        } else if (parameter.getParameterType() == Optional.class) {
            return Optional.ofNullable(ip);
        } else {
            return null;
        }
    }

}
