/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.mvc.troubleshooting;

import com.github.yingzhuo.carnival.common.mvc.AbstractHandlerInterceptorSupport;
import com.github.yingzhuo.carnival.troubleshooting.IgnoreTroubleshooting;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;

/**
 * 日志输出拦截器 (不建议生产环境使用)
 *
 * @author 应卓
 */
@Slf4j(topic = "troubleshooting")
public class TroubleshootingInterceptor extends AbstractHandlerInterceptorSupport {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        if (!log.isDebugEnabled()) {
            return true;
        }

        if (hasMethodOrClassAnnotation(IgnoreTroubleshooting.class, handler)) {
            return true;
        }

        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        try {
            doLog(request, (HandlerMethod) handler);
        } catch (Throwable e) {
            // NOP
        }

        return true;
    }

    private void doLog(HttpServletRequest request, HandlerMethod handlerMethod) {
        log.debug(StringUtils.repeat('-', 120));

        log.debug("[Path]:");
        log.debug("\t\t{}", decode(request.getRequestURI()));

        log.debug("[Method]:");
        log.debug("\t\t{}", request.getMethod());

        log.debug("[Headers]:");
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String name = headerNames.nextElement();
            String value = request.getHeader(name);
            log.debug("\t\t{} = {}", name, name.equalsIgnoreCase("cookie") ? StringUtils.abbreviate(value, 60) : value);
        }

        Locale locale = getLocale();
        if (locale != null) {
            log.debug("[Locale]:");
            log.debug("\t\t{}", getLocale());
        }

        final Map<String, String[]> parameterMap = request.getParameterMap();

        log.debug("[Params]:");
        Enumeration<String> paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String name = paramNames.nextElement();
            String[] values = parameterMap.get(name);
            if (values != null && values.length != 0) {
                for (String value : values) {
                    log.debug("\t\t{} = {}", name, value);
                }
            }
        }

        if (handlerMethod != null) {
            final Method method = handlerMethod.getMethod();
            final Class<?> type = handlerMethod.getBeanType();
            boolean typeDeprecated = type.getAnnotation(Deprecated.class) != null;
            boolean methodDeprecated = method.getAnnotation(Deprecated.class) != null;

            log.debug("[Controller]:");
            if (typeDeprecated) {
                log.warn("\t\ttype = {} [Deprecated !!!]", type.getName());
            } else {
                log.debug("\t\ttype = {}", type.getName());
            }

            if (methodDeprecated) {
                log.warn("\t\tmethod-name = {}(...) [Deprecated !!!]", method.getName());
            } else {
                log.debug("\t\tmethod-name = {}(...)", method.getName());
            }
        }

        log.debug(StringUtils.repeat('-', 120));
    }

    private Locale getLocale() {
        try {
            return LocaleContextHolder.getLocale();
        } catch (Exception e) {
            return null;
        }
    }

    private String decode(String path) {
        try {
            return URLDecoder.decode(path, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new AssertionError();
        }
    }

}
