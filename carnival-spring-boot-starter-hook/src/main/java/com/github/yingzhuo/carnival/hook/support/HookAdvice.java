/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.hook.support;

import com.github.yingzhuo.carnival.aop.AbstractAroundAdvice;
import com.github.yingzhuo.carnival.hook.Hook;
import com.github.yingzhuo.carnival.hook.PostHook;
import com.github.yingzhuo.carnival.hook.PreHook;
import com.github.yingzhuo.carnival.hook.impl.SimpleHookExecutionContext;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author 应卓
 */
@Aspect
public class HookAdvice extends AbstractAroundAdvice implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Around("@annotation(com.github.yingzhuo.carnival.hook.Hook)")
    public Object around(ProceedingJoinPoint call) throws Throwable {
        Hook annotation = getMethodAnnotation(call, Hook.class);

        if (annotation == null) {
            return call.proceed();
        }

        for (String beanName : annotation.pre()) {
            PreHook hook = applicationContext.getBean(beanName, PreHook.class);
            hook.execute(new SimpleHookExecutionContext(call));
        }

        Throwable ex = null;
        Object result;

        try {
            result = call.proceed();
        } catch (Throwable throwable) {
            result = null;
            ex = throwable;
        }

        for (String beanName : annotation.post()) {
            PostHook hook = applicationContext.getBean(beanName, PostHook.class);
            hook.execute(new SimpleHookExecutionContext(call, ex, result));
        }

        if (ex != null) {
            throw ex;
        } else {
            return result;
        }
    }

}
