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

import com.github.yingzhuo.carnival.debug.support.LogLevel;
import com.github.yingzhuo.carnival.debug.support.LoggerWrapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;

/**
 * 日志拦截器
 */
public class DebugMvcInterceptor extends HandlerInterceptorAdapter {

    private boolean enabled;
    private LoggerWrapper logger;

    public DebugMvcInterceptor(boolean enabled, LogLevel logLevel, String loggerName) {
        this.enabled = enabled;
        this.logger = new LoggerWrapper(logLevel, loggerName);
        if (logLevel == LogLevel.OFF) {
            this.enabled = false;
        }
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (enabled) {
            doLog(request, (HandlerMethod) handler);
        }
        return true;
    }

    private void doLog(HttpServletRequest request, HandlerMethod handlerMethod) {
        logger.doLog(StringUtils.repeat('-', 120));

        logger.doLog("[Path]: ");
        logger.doLog("\t\t\t{}", request.getRequestURI());

        logger.doLog("[Method]: ");
        logger.doLog("\t\t\t{}", request.getMethod());

        logger.doLog("[Headers]: ");
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String name = headerNames.nextElement();
            String value = request.getHeader(name);
            logger.doLog("\t\t\t{} = {}", name, name.equalsIgnoreCase("cookie") ? StringUtils.abbreviate(value, 60) : value);
        }

        logger.doLog("[Params]: ");
        Enumeration<String> paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String name = paramNames.nextElement();
            String value = request.getParameter(name);
            logger.doLog("\t\t\t{} = {}", name, value);
        }

        if (handlerMethod != null) {
            logger.doLog("[Controller]: ");
            logger.doLog("\t\t\ttype = {}", handlerMethod.getBeanType().getName());
            logger.doLog("\t\t\tmethod-name = {}", handlerMethod.getMethod().getName());
        }

        logger.doLog(StringUtils.repeat('-', 120));
    }

}
