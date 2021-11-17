/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.security.core;

import org.springframework.http.HttpHeaders;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.function.Predicate;

/**
 * @author 应卓
 * @since 1.10.36
 */
public final class LoggingFilterSkipPredicateFactories {

    private static final PathMatcher ANT_PATH_MATCHERS = new AntPathMatcher();

    public LoggingFilterSkipPredicateFactories() {
    }

    public static Predicate<HttpServletRequest> pathMatches(final String antPattern) {
        return request -> ANT_PATH_MATCHERS.match(antPattern, request.getRequestURI());
    }

    public static Predicate<HttpServletRequest> hasHeader(final String headerName) {
        return request -> request.getHeader(headerName) != null;
    }

    public static Predicate<HttpServletRequest> headerValueMatches(final String headerName, final String headerValueRegex) {
        return new Predicate<HttpServletRequest>() {
            @Override
            public boolean test(HttpServletRequest request) {
                String value = request.getHeader(headerName);
                if (value == null) return false;
                return value.matches(headerValueRegex);
            }
        };
    }

    @Deprecated
    public static Predicate<HttpServletRequest> userAgentMatches(final String regex) {
        return headerValueMatches(HttpHeaders.USER_AGENT, regex);
    }

}
