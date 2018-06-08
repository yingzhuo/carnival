/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.restful.security.core;

import com.github.yingzhuo.carnival.restful.security.RequiresAuthentication;
import com.github.yingzhuo.carnival.restful.security.RequiresGuest;
import com.github.yingzhuo.carnival.restful.security.RequiresPermissions;
import com.github.yingzhuo.carnival.restful.security.RequiresRoles;
import com.github.yingzhuo.carnival.restful.security.cache.CacheManager;
import com.github.yingzhuo.carnival.restful.security.listener.AuthenticationListener;
import com.github.yingzhuo.carnival.restful.security.parser.TokenParser;
import com.github.yingzhuo.carnival.restful.security.realm.UserDetailsRealm;
import com.github.yingzhuo.carnival.restful.security.token.Token;
import com.github.yingzhuo.carnival.restful.security.userdetails.UserDetails;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Locale;
import java.util.Optional;

/**
 * @author 应卓
 */
public final class RestfulSecurityInterceptor extends HandlerInterceptorAdapter {

    private LocaleResolver localeResolver = new DefaultLocaleLocaleResolver();
    private TokenParser tokenParser;
    private UserDetailsRealm userDetailsRealm;
    private AuthenticationListener authenticationListener;
    private CacheManager cacheManager;

    public RestfulSecurityInterceptor() {
        super();
    }

    public RestfulSecurityInterceptor(TokenParser tokenParser, UserDetailsRealm userDetailsRealm, AuthenticationListener authenticationListener, CacheManager cacheManager) {
        this.tokenParser = tokenParser;
        this.userDetailsRealm = userDetailsRealm;
        this.authenticationListener = authenticationListener;
        this.cacheManager = cacheManager;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

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

        Locale locale = localeResolver.resolveLocale(request);

        final NativeWebRequest webRequest = new ServletWebRequest(request, response);
        Optional<Token> tokenOp = tokenParser.parse(webRequest, locale);

        if (tokenOp.isPresent()) {
            Token token = tokenOp.get();
            RestfulSecurityContext.setToken(token);

            Optional<UserDetails> userDetailsOp;
            Optional<UserDetails> cached = cacheManager.getUserDetails(token);

            if (cached.isPresent()) {
                userDetailsOp = cached;
            } else {
                userDetailsOp = userDetailsRealm.loadUserDetails(tokenOp.get());
                userDetailsOp.ifPresent(ud -> cacheManager.saveUserDetails(token, ud));
            }

            userDetailsOp.ifPresent(userDetails -> authenticationListener.onAuthenticated(new ServletWebRequest(request), userDetails, getMethod(handler)));
            RestfulSecurityContext.setUserDetails(userDetailsOp.orElse(null));
        }

        CheckUtils.check(requiresAuthentication);
        CheckUtils.check(requiresGuest);
        CheckUtils.check(requiresRoles);
        CheckUtils.check(requiresPermissions);

        return true;
    }

    private Method getMethod(Object handler) {
        return ((HandlerMethod) handler).getMethod();
    }

    private <A extends Annotation> A getMethodAnnotation(Class<A> annotationType, Object handler) {
        return ((HandlerMethod) handler).getMethodAnnotation(annotationType);
    }

    public TokenParser getTokenParser() {
        return tokenParser;
    }

    public void setTokenParser(TokenParser tokenParser) {
        this.tokenParser = tokenParser;
    }

    public UserDetailsRealm getUserDetailsRealm() {
        return userDetailsRealm;
    }

    public void setUserDetailsRealm(UserDetailsRealm userDetailsRealm) {
        this.userDetailsRealm = userDetailsRealm;
    }

    public AuthenticationListener getAuthenticationListener() {
        return authenticationListener;
    }

    public void setAuthenticationListener(AuthenticationListener authenticationListener) {
        this.authenticationListener = authenticationListener;
    }

    public CacheManager getCacheManager() {
        return cacheManager;
    }

    public void setCacheManager(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    public void setLocaleResolver(LocaleResolver localeResolver) {
        this.localeResolver = localeResolver;
    }

    // ----------------------------------------------------------------------------------------------------------------

    private static class DefaultLocaleLocaleResolver implements LocaleResolver {

        @Override
        public Locale resolveLocale(HttpServletRequest request) {
            return Locale.getDefault();
        }

        @Override
        public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {
            // 无动作
        }
    }
}
