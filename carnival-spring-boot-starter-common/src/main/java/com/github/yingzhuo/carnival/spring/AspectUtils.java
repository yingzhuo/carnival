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

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

/**
 * @author 应卓
 * @since 1.7.13
 */
public final class AspectUtils {

    private AspectUtils() {
    }

    public static Method getMethod(JoinPoint joinPoint) {
        return ((MethodSignature) joinPoint.getSignature()).getMethod();
    }

    public static Class<?> getBeanType(JoinPoint joinPoint) {
        return joinPoint.getTarget().getClass();
    }

    public static boolean isDeprecated(JoinPoint joinPoint) {
        final Method method = getMethod(joinPoint);
        if (method.getAnnotation(Deprecated.class) != null) return true;
        final Class<?> beanType = getBeanType(joinPoint);
        if (beanType.getAnnotation(Deprecated.class) != null) return true;
        return false;
    }

}
