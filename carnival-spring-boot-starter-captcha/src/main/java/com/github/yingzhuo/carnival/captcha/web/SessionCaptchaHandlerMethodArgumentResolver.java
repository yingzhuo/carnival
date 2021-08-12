/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.captcha.web;

import com.github.yingzhuo.carnival.captcha.SessionCaptcha;
import com.github.yingzhuo.carnival.captcha.dao.HttpSessionCaptchaDao;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 应卓
 * @since 1.10.6
 */
public class SessionCaptchaHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

    private final String sessionAttributeName;

    public SessionCaptchaHandlerMethodArgumentResolver() {
        this(HttpSessionCaptchaDao.ATTRIBUTE_NAME);
    }

    public SessionCaptchaHandlerMethodArgumentResolver(String sessionAttributeName) {
        this.sessionAttributeName = sessionAttributeName;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(SessionCaptcha.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        return ((HttpServletRequest) webRequest.getNativeRequest()).getSession(true).getAttribute(sessionAttributeName);
    }

}
