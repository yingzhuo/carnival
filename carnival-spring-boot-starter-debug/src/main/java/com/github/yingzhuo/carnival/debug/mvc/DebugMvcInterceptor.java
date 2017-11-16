/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.debug.mvc;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;

/**
 * 日志拦截器
 */
public class DebugMvcInterceptor extends HandlerInterceptorAdapter {

    private final boolean enabled;
    private static Logger LOGGER = LoggerFactory.getLogger(DebugMvcInterceptor.class);

    public DebugMvcInterceptor(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (enabled) {
            doLog(request, (HandlerMethod) handler);
        }
        return true;
    }

    private void doLog(HttpServletRequest request, HandlerMethod handlerMethod) {
        LOGGER.debug(StringUtils.repeat('-', 120));

        LOGGER.debug("[Path]: ");
        LOGGER.debug("\t\t\t{}", request.getRequestURI());

        LOGGER.debug("[Method]: ");
        LOGGER.debug("\t\t\t{}", request.getMethod());

        LOGGER.debug("[Headers]: ");
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String name = headerNames.nextElement();
            String value = request.getHeader(name);
            LOGGER.debug("\t\t\t{} = {}", name, name.equalsIgnoreCase("cookie") ? StringUtils.abbreviate(value, 60) : value);
        }

        LOGGER.debug("[Params]: ");
        Enumeration<String> paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String name = paramNames.nextElement();
            String value = request.getParameter(name);
            LOGGER.debug("\t\t\t{} = {}", name, value);
        }

        if (handlerMethod != null) {
            LOGGER.debug("[Controller]: ");
            LOGGER.debug("\t\t\ttype = {}", handlerMethod.getBeanType().getName());
            LOGGER.debug("\t\t\tmethod-name = {}", handlerMethod.getMethod().getName());
        }

        LOGGER.debug(StringUtils.repeat('-', 120));
    }

}
