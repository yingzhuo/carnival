/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.restful.security.params.core;

import com.github.yingzhuo.carnival.common.mvc.AbstractHandlerInterceptorSupport;
import com.github.yingzhuo.carnival.restful.security.params.Params;
import com.github.yingzhuo.carnival.restful.security.params.ParamsImpl;
import com.github.yingzhuo.carnival.restful.security.params.ParamsValidatingAlgorithm;
import com.github.yingzhuo.carnival.restful.security.params.ParamsValidatingIgnored;
import com.github.yingzhuo.carnival.restful.security.params.exception.InvalidRequestException;
import com.github.yingzhuo.carnival.restful.security.params.exception.InvalidSignException;
import com.github.yingzhuo.carnival.restful.security.params.exception.InvalidTimestampException;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Duration;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author 应卓
 * @since 1.6.30
 */
@Slf4j
@Setter
public class ParamsValidatingInterceptor extends AbstractHandlerInterceptorSupport {

    private final PathMatcher pathMatcher = new AntPathMatcher();
    private ParamsValidatingAlgorithm algorithm = ParamsValidatingAlgorithm.getDefault();
    private Set<String> excludeAntPatterns;
    private String nonceParameterName;
    private String timestampParameterName;
    private String signParameterName;
    private String nonceHeaderName;
    private String timestampHeaderName;
    private String signHeaderName;
    private Duration maxAllowedTimestampDiff;
    private boolean debugMode = false;

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        ParamsValidatingContext.remove();
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        final String path = request.getRequestURI();

        if (excludeAntPatterns != null && !excludeAntPatterns.isEmpty()) {
            boolean skip = excludeAntPatterns.stream().anyMatch(pattern -> pathMatcher.match(pattern, path));
            if (skip) {
                return true;
            }
        }

        if (hasMethodOrClassAnnotation(ParamsValidatingIgnored.class, handler)) {
            return true;
        }

        // 清空上下文
        ParamsValidatingContext.remove();

        final Params params = resolve(request);
        if (!params.isValid()) {
            if (debugMode) {
                log.warn("invalid Params's instance = {}", params);
                return true;
            } else {
                throw new InvalidRequestException("invalid request", request);
            }
        } else {
            ParamsValidatingContext.set(params);
        }

        final String parametersAsString = flatAndSort(request.getParameterMap(), signParameterName);
        final String hashedParameters = algorithm.encode(parametersAsString);

        // 检查签名
        final String sign = params.getSign();

        if (algorithm.notMatches(hashedParameters, sign)) {
            if (debugMode) {
                log.warn("invalid sign");
                log.warn("actual-sign = {}", sign);
                log.warn("expected-sign = {}", hashedParameters);
                return true;
            } else {
                throw new InvalidSignException("invalid sign", request);
            }
        }

        // 检查时间戳
        if (!isTimestampCheckingDisabled()) {
            long now = System.currentTimeMillis();
            long diff = Math.abs(now - params.getTimestamp());
            if (diff > maxAllowedTimestampDiff.toMillis()) {
                if (debugMode) {
                    log.warn("invalid timestamp");
                    log.warn("actual-timestamp = {}", params.getTimestamp());
                    log.warn("server-timestamp = {}", now);
                    return true;
                } else {
                    throw new InvalidTimestampException("invalid timestamp", request);
                }
            }
        }
        return true;
    }

    public Params resolve(HttpServletRequest request) {
        String nonce = resolveNonce(request);
        Long timestamp = resolveTimestamp(request);
        String sign = resolveSign(request);
        return new ParamsImpl(nonce, timestamp, sign);
    }

    private String resolveNonce(HttpServletRequest request) {
        String value = null;
        if (StringUtils.isNotBlank(nonceParameterName)) {
            value = StringUtils.defaultIfBlank(request.getParameter(nonceParameterName), null);
        }
        if (value == null && StringUtils.isNotBlank(nonceHeaderName)) {
            value = StringUtils.defaultIfBlank(request.getHeader(nonceHeaderName), null);
        }
        return StringUtils.defaultIfBlank(value, null);
    }

    private Long resolveTimestamp(HttpServletRequest request) {
        Long value = null;
        if (StringUtils.isNotBlank(timestampParameterName)) {
            value = string2Long(request.getParameter(timestampParameterName));
        }
        if (value == null && StringUtils.isNotBlank(timestampHeaderName)) {
            value = string2Long(request.getHeader(timestampHeaderName));
        }
        if (isTimestampCheckingDisabled() && value == null) {
            value = -1L;
        }
        return value;
    }

    private String resolveSign(HttpServletRequest request) {
        String value = null;
        if (StringUtils.isNotBlank(signParameterName)) {
            value = StringUtils.defaultIfBlank(request.getParameter(signParameterName), null);
        }
        if (value == null && signHeaderName != null) {
            value = StringUtils.defaultIfBlank(request.getHeader(signHeaderName), null);
        }
        return StringUtils.defaultIfBlank(value, null);
    }

    private String flatAndSort(Map<String, String[]> source, String signParameterName) {
        final StringBuilder stringBuilder = new StringBuilder();

        for (String key : new TreeSet<>(source.keySet())) {

            if (signParameterName != null && signParameterName.equals(key)) {
                continue;
            }

            String[] values = source.get(key);
            String value = StringUtils.join(values, ",");
            stringBuilder.append(
                    String.format("%s=%s,", key, value)
            );
        }

        String string = stringBuilder.toString();
        if (string.endsWith(",")) {
            string = string.substring(0, string.length() - 1);
        }
        return string;
    }

    private Long string2Long(String s) {
        try {
            return Long.parseLong(s);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private boolean isTimestampCheckingDisabled() {
        return this.maxAllowedTimestampDiff == null;
    }

}
