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
import org.springframework.lang.NonNull;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author 应卓
 * @see LoggingFilterFactory
 * @since 1.10.10
 */
public class LoggingFilter extends OncePerRequestFilter {

    private final ConfigurableLogger log;
    private final Set<Predicate<HttpServletRequest>> skips;

    public LoggingFilter(ConfigurableLogger log) {
        this(log, (Predicate<HttpServletRequest>[]) null);
    }

    @SafeVarargs
    public LoggingFilter(ConfigurableLogger log, Predicate<HttpServletRequest>... skips) {
        this.log = log;
        this.skips = skips == null ? null : Stream.of(skips).collect(Collectors.toSet());
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
        if (!skip(request) && log.isEnabled()) {
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

    private boolean skip(HttpServletRequest request) {
        if (skips == null || skips.isEmpty()) {
            return false;
        }
        return skips.stream().anyMatch(p -> p.test(request));
    }

}
