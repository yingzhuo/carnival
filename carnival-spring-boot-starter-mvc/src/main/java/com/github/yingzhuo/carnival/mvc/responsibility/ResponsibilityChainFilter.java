/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.mvc.responsibility;

import com.github.yingzhuo.carnival.spring.PathMatcherUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 应卓
 * @since 1.6.14
 */
public class ResponsibilityChainFilter extends OncePerRequestFilter {

    private final ResponsibilityChain chain;

    public ResponsibilityChainFilter(ResponsibilityChain chain) {
        this.chain = chain;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if (chain != null && !chain.isEmpty()) {

            final String path = request.getRequestURI();

            for (Responsibility responsibility : chain) {

                final String[] excludePatterns = responsibility.excludePatterns();

                if (excludePatterns != null && excludePatterns.length > 0) {
                    if (PathMatcherUtils.anyMatch(path, excludePatterns)) {
                        continue;
                    }
                }

                responsibility.execute(new ServletWebRequest(request, response));
            }
        }

        filterChain.doFilter(request, response);
    }

}
