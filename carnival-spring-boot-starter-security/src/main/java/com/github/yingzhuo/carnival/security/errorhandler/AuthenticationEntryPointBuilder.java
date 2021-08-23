/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.security.errorhandler;

import org.springframework.http.HttpMethod;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.DelegatingAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.ELRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.LinkedHashMap;

/**
 * @author 应卓
 * @see AuthenticationEntryPoint
 * @see DelegatingAuthenticationEntryPoint
 * @see ELRequestMatcher
 * @see AntPathRequestMatcher
 * @since 1.10.11
 */
public final class AuthenticationEntryPointBuilder {

    private final LinkedHashMap<RequestMatcher, AuthenticationEntryPoint> map = new LinkedHashMap<>();
    private AuthenticationEntryPoint defaultEntryPoint;

    private AuthenticationEntryPointBuilder() {
    }

    public static AuthenticationEntryPointBuilder newInstance() {
        return new AuthenticationEntryPointBuilder();
    }

    public AuthenticationEntryPointBuilder add(RequestMatcher matcher, AuthenticationEntryPoint delegate) {
        map.put(matcher, delegate);
        return this;
    }

    public AuthenticationEntryPointBuilder el(String elExp, AuthenticationEntryPoint delegate) {
        return add(new ELRequestMatcher(elExp), delegate);
    }

    public AuthenticationEntryPointBuilder ant(String antPattern, AuthenticationEntryPoint delegate) {
        return add(new AntPathRequestMatcher(antPattern), delegate);
    }

    public AuthenticationEntryPointBuilder ant(HttpMethod httpMethod, String antPattern, AuthenticationEntryPoint delegate) {
        return add(new AntPathRequestMatcher(antPattern, httpMethod.toString()), delegate);
    }

    public AuthenticationEntryPointBuilder defaultEntryPoint(AuthenticationEntryPoint defaultEntryPoint) {
        this.defaultEntryPoint = defaultEntryPoint;
        return this;
    }

    public DelegatingAuthenticationEntryPoint build() {
        final DelegatingAuthenticationEntryPoint bean = new DelegatingAuthenticationEntryPoint(map);
        bean.setDefaultEntryPoint(defaultEntryPoint);
        bean.afterPropertiesSet();
        return bean;
    }

}
