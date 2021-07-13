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

import com.github.yingzhuo.carnival.common.mvc.AbstractHandlerInterceptorSupport;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 应卓
 * @since 1.9.11
 */
@Slf4j
public class ForkPlatformTransactionManagerInterceptor
        extends AbstractHandlerInterceptorSupport
        implements Ordered {

    private static final int DEFAULT_ORDER = 0;

    private final ForkPlatformTransactionManager txManager;
    private final int order;

    public ForkPlatformTransactionManagerInterceptor(ForkPlatformTransactionManager txManager) {
        this(txManager, DEFAULT_ORDER);
    }

    public ForkPlatformTransactionManagerInterceptor(ForkPlatformTransactionManager txManager, int order) {
        this.txManager = txManager;
        this.order = order;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        final ForkPlatformTransactionManagerSwitch annotation = super.getMethodOrClassAnnotation(ForkPlatformTransactionManagerSwitch.class, handler).orElse(null);
        if (annotation != null && txManager != null) {
            log.trace("tx-manager switch to {}", annotation.value());
            txManager.getLookup().set(annotation.value());
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        if (txManager != null) {
            txManager.getLookup().reset();
        }
    }

    @Override
    public int getOrder() {
        return this.order;
    }

}
