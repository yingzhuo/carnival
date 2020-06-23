/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.shield.core;

import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;

/**
 * @author 应卓
 * @since 1.6.21
 */
abstract class AbstractShieldFilter extends OncePerRequestFilter {

    private final RequestMappingHandlerMapping mappings;
    private final PathMatcher pathMatcher = new AntPathMatcher();
    private Set<String> skipPatterns = Collections.emptySet();

    public AbstractShieldFilter(RequestMappingHandlerMapping mappings) {
        this.mappings = Objects.requireNonNull(mappings);
    }

    protected final HandlerMethod getHandlerMethod(HttpServletRequest request) {
        try {
            final HandlerExecutionChain handlerExecutionChain = mappings.getHandler(request);
            return (HandlerMethod) handlerExecutionChain.getHandler();
        } catch (Exception e) {
            return null;
        }
    }

    protected boolean shouldSkip(HttpServletRequest request) {
        if (skipPatterns == null || skipPatterns.isEmpty()) {
            return false;
        }

        final String path = request.getRequestURI();
        return skipPatterns
                .stream()
                .anyMatch(pattern -> pathMatcher.match(pattern, path));
    }

    public final void setSkipPatterns(Set<String> skipPatterns) {
        this.skipPatterns = skipPatterns;
    }

}
