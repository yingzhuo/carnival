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

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author 应卓
 * @see SpringUtils
 * @since 1.10.14
 */
public final class RequestMappingUtils {

    private RequestMappingUtils() {
    }

    public static RequestMappingHandlerMapping getRequestMappingHandlerMapping() {
        return SpringUtils.getBean(RequestMappingHandlerMapping.class);
    }

    public static HandlerMethod getHandlerMethod(HttpServletRequest request) {
        try {
            return (HandlerMethod) getRequestMappingHandlerMapping()
                    .getHandler(request)
                    .getHandler();
        } catch (Exception e) {
            return null;
        }
    }

    public static RequestMappingHandlerAdapter getRequestMappingHandlerAdapter() {
        return SpringUtils.getBean(RequestMappingHandlerAdapter.class);
    }

    public static List<HandlerMethodArgumentResolver> getHandlerMethodArgumentResolvers() {
        return getRequestMappingHandlerAdapter().getArgumentResolvers();
    }

    public static List<HandlerMethodReturnValueHandler> getHandlerMethodReturnValueHandlers() {
        return getRequestMappingHandlerAdapter().getReturnValueHandlers();
    }

}
