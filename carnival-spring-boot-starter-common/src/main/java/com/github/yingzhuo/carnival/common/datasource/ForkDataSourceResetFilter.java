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

import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 应卓
 * @since 1.0.3
 */
public class ForkDataSourceResetFilter extends OncePerRequestFilter implements javax.servlet.Filter {

    private final boolean clearDataSourceKeyBeforeHandle;
    private final boolean clearDataSourceKeyAfterHandle;

    public ForkDataSourceResetFilter() {
        this(true, true);
    }

    public ForkDataSourceResetFilter(boolean clearDataSourceKeyBeforeHandle, boolean clearDataSourceKeyAfterHandle) {
        this.clearDataSourceKeyBeforeHandle = clearDataSourceKeyBeforeHandle;
        this.clearDataSourceKeyAfterHandle = clearDataSourceKeyAfterHandle;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (clearDataSourceKeyBeforeHandle) {
            ForkDataSource.reset();
        }

        filterChain.doFilter(request, response);

        if (clearDataSourceKeyAfterHandle) {
            ForkDataSource.reset();
        }
    }

}
