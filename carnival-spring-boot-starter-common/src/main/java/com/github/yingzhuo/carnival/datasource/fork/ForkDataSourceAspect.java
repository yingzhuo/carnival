/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.datasource.fork;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author 应卓
 * @since 1.6.0
 */
@Slf4j
@Aspect
public class ForkDataSourceAspect implements ApplicationContextAware {

    private ForkDataSource forkDataSource;

    @Around("@annotation(com.github.yingzhuo.carnival.datasource.fork.ForkDataSourceSwitch)")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {

        final ForkDataSourceSwitch annotation = ((MethodSignature) pjp.getSignature()).getMethod().getAnnotation(ForkDataSourceSwitch.class);

        if (annotation != null && forkDataSource != null) {
            log.trace("datasource switch to {}", annotation.value());
            forkDataSource.getLookup().set(annotation.value());
        }

        try {
            return pjp.proceed();
        } finally {
            if (annotation != null && forkDataSource != null) {
                forkDataSource.getLookup().reset();
            }
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws NoSuchBeanDefinitionException {
        try {
            this.forkDataSource = applicationContext.getBean(ForkDataSource.class);
        } catch (NoSuchBeanDefinitionException e) {
            this.forkDataSource = null;
        }
    }

}
