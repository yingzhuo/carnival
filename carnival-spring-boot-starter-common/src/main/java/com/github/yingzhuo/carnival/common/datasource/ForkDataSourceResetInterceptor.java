/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.common.datasource;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 应卓
 * @since 1.0.3
 */
@Deprecated
public class ForkDataSourceResetInterceptor implements HandlerInterceptor {

    private final boolean clearDataSourceKeyBeforeHandle;
    private final boolean clearDataSourceKeyAfterHandle;

    public ForkDataSourceResetInterceptor() {
        this(true, true);
    }

    public ForkDataSourceResetInterceptor(boolean clearDataSourceKeyBeforeHandle, boolean clearDataSourceKeyAfterHandle) {
        this.clearDataSourceKeyBeforeHandle = clearDataSourceKeyBeforeHandle;
        this.clearDataSourceKeyAfterHandle = clearDataSourceKeyAfterHandle;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (clearDataSourceKeyBeforeHandle) {
            ForkDataSource.reset();
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        if (clearDataSourceKeyAfterHandle) {
            ForkDataSource.reset();
        }
    }

}
