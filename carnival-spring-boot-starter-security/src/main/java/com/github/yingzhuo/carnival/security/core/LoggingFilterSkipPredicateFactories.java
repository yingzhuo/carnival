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

import javax.servlet.http.HttpServletRequest;
import java.util.function.Predicate;

/**
 * @author 应卓
 * @since 1.10.36
 */
public final class LoggingFilterSkipPredicateFactories {

    public LoggingFilterSkipPredicateFactories() {
    }

    public static Predicate<HttpServletRequest> userAgentMatches(final String regex) {
        return request -> {
            final String value = request.getHeader(HttpHeaders.USER_AGENT);
            if (value == null) {
                return false;
            } else {
                return value.matches(regex);
            }
        };
    }

}
