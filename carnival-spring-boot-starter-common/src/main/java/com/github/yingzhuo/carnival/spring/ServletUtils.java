/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.spring;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletContext;
import javax.servlet.http.*;

/**
 * @author 应卓
 * @see SpringUtils
 */
public final class ServletUtils {

    private ServletUtils() {
    }

    // -----------------------------------------------------------------------------------------------------------------

    public static HttpServletRequest getRequest() {
        final ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        return attributes.getRequest();
    }

    public static HttpServletRequest getUnwrappedRequest() {
        HttpServletRequest request = getRequest();

        while (request instanceof HttpServletRequestWrapper) {
            request = (HttpServletRequest) ((HttpServletRequestWrapper) request).getRequest();
        }

        return request;
    }

    public static HttpServletResponse getResponse() {
        final ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        return attributes.getResponse();
    }

    public static HttpServletResponse getUnwrappedResponse() {
        HttpServletResponse response = getResponse();

        while (response instanceof HttpServletResponseWrapper) {
            response = (HttpServletResponse) ((HttpServletResponseWrapper) response).getResponse();
        }

        return response;
    }

    public static HttpSession getSession() {
        return getSession(true);
    }

    public static HttpSession getSession(boolean create) {
        return getRequest().getSession(create);
    }

    public static String getSessionId() {
        return getSession().getId();
    }

    public static ServletContext getServletContext() {
        return getRequest().getServletContext();
    }

}
