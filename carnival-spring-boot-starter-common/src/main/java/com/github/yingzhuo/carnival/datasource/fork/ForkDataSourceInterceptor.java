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

import com.github.yingzhuo.carnival.common.mvc.AbstractHandlerInterceptorSupport;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * @author 应卓
 * @since 1.6.0
 */
@Slf4j
public class ForkDataSourceInterceptor extends AbstractHandlerInterceptorSupport implements Ordered {

    private final ForkDataSource forkDataSource;
    private final int order;

    public ForkDataSourceInterceptor(ForkDataSource dataSource) {
        this(dataSource, 0);
    }

    public ForkDataSourceInterceptor(ForkDataSource dataSource, int order) {
        this.forkDataSource = Objects.requireNonNull(dataSource);
        this.order = order;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        final ForkDataSourceSwitch annotation = super.getMethodOrClassAnnotation(ForkDataSourceSwitch.class, handler).orElse(null);
        if (annotation != null && forkDataSource != null) {
            log.trace("datasource switch to {}", annotation.value());
            forkDataSource.getLookup().set(annotation.value());
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        if (forkDataSource != null) {
            forkDataSource.getLookup().reset();
        }
    }

    @Override
    public int getOrder() {
        return this.order;
    }

}
