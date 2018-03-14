/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.restful.security.impl;

import com.github.yingzhuo.carnival.restful.security.*;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.util.Optional;

/**
 * @author 应卓
 */
public final class RestfulSecurityInterceptor extends HandlerInterceptorAdapter {

    private final TokenParser tokenParser;
    private final UserDetailsRealm userDetailsRealm;
    private final AuthenticationListener authenticationListener;
    private final RunAsIdGenerator runAsIdGenerator;

    public RestfulSecurityInterceptor(TokenParser tokenParser, UserDetailsRealm userDetailsRealm, AuthenticationListener authenticationListener, RunAsIdGenerator runAsIdGenerator) {
        this.tokenParser = tokenParser;
        this.userDetailsRealm = userDetailsRealm;
        this.authenticationListener = authenticationListener;
        this.runAsIdGenerator = runAsIdGenerator;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        RunAs runAs = getMethodAnnotation(RunAs.class, handler);

        if (runAs != null) {
            RestfulSecurityContext.setUserDetails(
                    UserDetails.builder()
                            .id(runAsIdGenerator.nextId())
                            .roles(runAs.roles())
                            .permissions(runAs.permissions())
                            .build()
            );
        }

        RequiresAuthentication requiresAuthentication = getMethodAnnotation(RequiresAuthentication.class, handler);
        RequiresGuest requiresGuest = getMethodAnnotation(RequiresGuest.class, handler);
        RequiresPermissions requiresPermissions = getMethodAnnotation(RequiresPermissions.class, handler);
        RequiresRoles requiresRoles = getMethodAnnotation(RequiresRoles.class, handler);

        // 被拦截方法没有任何元注释
        if (requiresAuthentication == null &&
                requiresGuest == null &&
                requiresRoles == null &&
                requiresPermissions == null) {
            return true;
        }

        final NativeWebRequest webRequest = new ServletWebRequest(request, response);
        Optional<Token> tokenOptional = tokenParser.parse(webRequest);
        tokenOptional.ifPresent(RestfulSecurityContext::setToken);

        if (tokenOptional.isPresent()) {
            Optional<UserDetails> userDetailsOptional = userDetailsRealm.loadUserDetails(tokenOptional.get());
            userDetailsOptional.ifPresent(authenticationListener::onAuthenticated);
            RestfulSecurityContext.setUserDetails(userDetailsOptional.orElse(null));
        }

        CheckUtils.check(requiresAuthentication);
        CheckUtils.check(requiresGuest);
        CheckUtils.check(requiresRoles);
        CheckUtils.check(requiresPermissions);

        return true;
    }

    private <A extends Annotation> A getMethodAnnotation(Class<A> annotationType, Object handler) {
        return ((HandlerMethod) handler).getMethodAnnotation(annotationType);
    }

    public TokenParser getTokenParser() {
        return tokenParser;
    }

    public UserDetailsRealm getUserDetailsRealm() {
        return userDetailsRealm;
    }

    public AuthenticationListener getAuthenticationListener() {
        return authenticationListener;
    }

    public RunAsIdGenerator getRunAsIdGenerator() {
        return runAsIdGenerator;
    }
}
