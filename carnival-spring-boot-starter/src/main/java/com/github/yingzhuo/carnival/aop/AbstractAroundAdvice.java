/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * @author 应卓
 */
public abstract class AbstractAroundAdvice {

    protected <A extends Annotation> A getMethodAnnotation(ProceedingJoinPoint call, Class<A> annotationType) {
        final String methodName = call.getSignature().getName();
        final MethodSignature methodSignature = (MethodSignature) call.getSignature();
        Method method = methodSignature.getMethod();
        A annotation = method.getAnnotation(annotationType);

        if (annotation == null) {
            try {
                method = call.getTarget().getClass().getDeclaredMethod(methodName, method.getParameterTypes());
                annotation = method.getAnnotation(annotationType);
            } catch (NoSuchMethodException e) {
                annotation = null;
            }
        }

        return annotation;
    }

}
