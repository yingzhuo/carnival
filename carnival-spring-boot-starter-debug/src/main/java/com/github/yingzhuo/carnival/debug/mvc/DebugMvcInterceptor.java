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
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Enumeration;

/**
 * 日志拦截器
 */
public class DebugMvcInterceptor extends HandlerInterceptorAdapter {

    private LoggerWrapper logger;

    public DebugMvcInterceptor(LogLevel logLevel, String loggerName) {
        this.logger = new LoggerWrapper(logLevel, loggerName);
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 静态资源不记录日志
        if (handler instanceof ResourceHttpRequestHandler) {
            return true;
        }

        try {
            doLog(request, (HandlerMethod) handler);
        } catch (Exception e) {
            // NOP
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
            final Method method = handlerMethod.getMethod();
            final Class<?> type = handlerMethod.getBeanType();
            boolean methodDeprecated = method.getAnnotation(Deprecated.class) != null;
            boolean typeDeprecated = type.getAnnotation(Deprecated.class) != null;

            logger.doLog("[Controller]: ");
            logger.doLog("\t\t\ttype = {}{}", method.getName(), typeDeprecated ? "(Deprecated)" : "");
            logger.doLog("\t\t\tmethod-name = {}{}", type.getName(), methodDeprecated ? "(Deprecated)" : "");
        }

        logger.doLog(StringUtils.repeat('-', 120));
    }

}
