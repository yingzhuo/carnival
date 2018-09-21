/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.common.mvc.interceptor;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.lang.annotation.Annotation;
import java.util.Objects;
import java.util.Optional;

/**
 * @author 应卓
 */
public abstract class HandlerInterceptorSupport implements HandlerInterceptor {

    /**
     * 获取方法上的元注释
     */
    protected final <A extends Annotation> Optional<A> getMethodAnnotation(Class<A> annotationType, Object handler) {

        Objects.requireNonNull(handler);
        Objects.requireNonNull(annotationType);

        if (!(handler instanceof HandlerMethod)) {
            return Optional.empty();
        }

        final HandlerMethod handlerMethod = (HandlerMethod) handler;
        final A annotation = ((HandlerMethod) handler).getMethodAnnotation(annotationType);
        return Optional.ofNullable(annotation);
    }

}
