/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.tx.fork;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.Ordered;

/**
 * @author 应卓
 * @since 1.9.11
 */
@Slf4j
@Aspect
public class ForkPlatformTransactionManagerAspect implements Ordered {

    private final ForkPlatformTransactionManager txManager;
    private final int order;

    public ForkPlatformTransactionManagerAspect(ForkPlatformTransactionManager txManager) {
        this(txManager, 0);
    }

    public ForkPlatformTransactionManagerAspect(ForkPlatformTransactionManager txManager, int order) {
        this.txManager = txManager;
        this.order = order;
    }

    @Around("@annotation(com.github.yingzhuo.carnival.tx.fork.ForkPlatformTransactionManagerSwitch)")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {

        final ForkPlatformTransactionManagerSwitch annotation = ((MethodSignature) pjp.getSignature()).getMethod().getAnnotation(ForkPlatformTransactionManagerSwitch.class);

        if (annotation != null && txManager != null) {
            log.trace("tx-manager switch to {}", annotation.value());
            txManager.getLookup().set(annotation.value());
        }

        try {
            return pjp.proceed();
        } finally {
            if (annotation != null && txManager != null) {
                txManager.getLookup().reset();
            }
        }
    }

    @Override
    public int getOrder() {
        return this.order;
    }

}
