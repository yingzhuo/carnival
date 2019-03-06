/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.patchca.support;

import com.github.yingzhuo.carnival.patchca.SessionPatchca;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

public class SessionPatchcaHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

    private final String sessionAttributeName;

    public SessionPatchcaHandlerMethodArgumentResolver(String sessionAttributeName) {
        this.sessionAttributeName = sessionAttributeName;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean c1 = parameter.hasParameterAnnotation(SessionPatchca.class) && parameter.getParameterType() == String.class;
        boolean c2 = parameter.hasParameterAnnotation(SessionPatchca.class) && parameter.getParameterType() == Optional.class;
        return c1 || c2;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        HttpSession session = webRequest.getNativeRequest(HttpServletRequest.class).getSession();
        String patchca = (String) session.getAttribute(this.sessionAttributeName);

        boolean c1 = parameter.hasParameterAnnotation(SessionPatchca.class) && parameter.getParameterType() == String.class;
        if (c1) {
            return patchca;
        }

        boolean c2 = parameter.hasParameterAnnotation(SessionPatchca.class) && parameter.getParameterType() == Optional.class;
        if (c2) {
            return Optional.ofNullable(patchca);
        }

        return null;
    }
}
