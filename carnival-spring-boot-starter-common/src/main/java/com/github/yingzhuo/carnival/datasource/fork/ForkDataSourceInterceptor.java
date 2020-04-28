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

import com.github.yingzhuo.carnival.common.mvc.interceptor.HandlerInterceptorSupport;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * @author 应卓
 * @since 1.6.0
 */
@Slf4j
public class ForkDataSourceInterceptor extends HandlerInterceptorSupport {

    private final ForkDataSource forkDataSource;

    public ForkDataSourceInterceptor(DataSource dataSource) {
        if (dataSource instanceof ForkDataSource) {
            this.forkDataSource = (ForkDataSource) dataSource;
        } else {
            throw new IllegalArgumentException("dataSource is not type of " + ForkDataSource.class.getName() + ".");
        }
    }

    public ForkDataSourceInterceptor(ForkDataSource dataSource) {
        this.forkDataSource = dataSource;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        ForkDataSourceSwitch annotation = super.getMethodAnnotation(ForkDataSourceSwitch.class, handler).orElse(null);
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

}
