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

import com.github.yingzhuo.carnival.common.log.ConfigurableLogger;
import lombok.val;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpMethod;
import org.springframework.lang.NonNull;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

/**
 * @author 应卓
 * @see LoggingFilterFactory
 * @since 1.10.10
 */
public class LoggingFilter extends OncePerRequestFilter {

    private static final PathMatcher ANT_PATH_MATCHER = new AntPathMatcher();
    private final ConfigurableLogger log;
    private final Set<Predicate<HttpServletRequest>> skips;

    public LoggingFilter(ConfigurableLogger log) {
        this(log, null);
    }

    public LoggingFilter(ConfigurableLogger log, Set<Predicate<HttpServletRequest>> skips) {
        this.log = log;
        this.skips = skips;
    }

    public static SkipPatternBuilder skipPatternBuilder() {
        return new SkipPatternBuilder();
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain chain) throws ServletException, IOException {
        try {
            doLog(request);
        } catch (Exception e) {
            // NOP
        }

        chain.doFilter(request, response);
    }

    private void doLog(HttpServletRequest request) {
        if (!shouldSkip(request) && log.isEnabled()) {
            log.log(StringUtils.repeat('-', 150));
            log.log("Method: {}", request.getMethod());
            log.log("Path: {}", request.getRequestURI());
            log.log("Parameters:");
            val requestParamNames = request.getParameterNames();
            while (requestParamNames.hasMoreElements()) {
                val requestParamName = requestParamNames.nextElement();
                val requestParamValue = request.getParameter(requestParamName);
                log.log("\t\t{} = {}", requestParamName, requestParamValue);
            }

            log.log("Headers:");
            val requestHeaderNames = request.getHeaderNames();
            while (requestHeaderNames.hasMoreElements()) {
                val requestHeaderName = requestHeaderNames.nextElement();
                val requestHeaderValue = request.getHeader(requestHeaderName);
                log.log("\t\t{} = {}", requestHeaderName, requestHeaderValue);
            }
            log.log(StringUtils.repeat('-', 150));
        }
    }

    private boolean shouldSkip(HttpServletRequest request) {
        if (skips == null || skips.isEmpty()) {
            return false;
        }
        return skips.stream().anyMatch(p -> p.test(request));
    }

    // ----------------------------------------------------------------------------------------------------------------

    public static class SkipPatternBuilder {
        private final Set<Predicate<HttpServletRequest>> predicates = new HashSet<>();

        private SkipPatternBuilder() {
        }

        public SkipPatternBuilder headerMatches(final String header, final String valueRegexPattern) {
            predicates.add(
                    request -> {
                        String headerValue = request.getHeader(header);
                        if (headerValue == null) {
                            return false;
                        }
                        return headerValue.matches(valueRegexPattern);
                    }
            );
            return this;
        }

        public SkipPatternBuilder pathMatches(final String antStylePattern) {
            predicates.add(
                    request -> ANT_PATH_MATCHER.match(antStylePattern, request.getRequestURI())
            );
            return this;
        }

        public SkipPatternBuilder pathMatches(final HttpMethod method, final String antStylePattern) {
            predicates.add(
                    request -> method.name().equalsIgnoreCase(request.getMethod()) &&
                            ANT_PATH_MATCHER.match(antStylePattern, request.getRequestURI())
            );
            return this;
        }

        public Set<Predicate<HttpServletRequest>> build() {
            return Collections.unmodifiableSet(predicates);
        }
    }

}
