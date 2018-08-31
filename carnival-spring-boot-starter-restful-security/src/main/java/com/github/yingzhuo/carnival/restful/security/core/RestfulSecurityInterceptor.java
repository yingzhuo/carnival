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

import com.github.yingzhuo.carnival.restful.security.AuthenticationStrategy;
import com.github.yingzhuo.carnival.restful.security.annotation.AuthenticationComponent;
import com.github.yingzhuo.carnival.restful.security.annotation.Requires;
import com.github.yingzhuo.carnival.restful.security.cache.CacheManager;
import com.github.yingzhuo.carnival.restful.security.listener.AuthenticationListener;
import com.github.yingzhuo.carnival.restful.security.parser.TokenParser;
import com.github.yingzhuo.carnival.restful.security.realm.UserDetailsRealm;
import com.github.yingzhuo.carnival.restful.security.token.Token;
import com.github.yingzhuo.carnival.restful.security.userdetails.UserDetails;
import com.github.yingzhuo.carnival.spring.SpringUtils;
import lombok.val;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @author 应卓
 */
public class RestfulSecurityInterceptor implements HandlerInterceptor {

    private Map<Method, AuthenticationComponent> cache1;
    private Map<Method, Annotation> cache2;

    private TokenParser tokenParser;
    private UserDetailsRealm userDetailsRealm;
    private AuthenticationListener authenticationListener;
    private CacheManager cacheManager;
    private LocaleResolver localeResolver = new NullLocaleResolver();
    private AuthenticationStrategy authenticationStrategy = AuthenticationStrategy.ONLY_ANNOTATED;
    private boolean initialized = false;

    public RestfulSecurityInterceptor() {
        super();
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (!initialized) {
            this.initCache(SpringUtils.getBean(RequestMappingHandlerMapping.class).getHandlerMethods().values());
        }

        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        val handlerMethod = (HandlerMethod) handler;
        AuthenticationComponent ac = cache1.get(handlerMethod.getMethod());
        Annotation annotation = cache2.get(handlerMethod.getMethod());

        if (ac == null && authenticationStrategy == AuthenticationStrategy.ONLY_ANNOTATED) {
            return true;
        }

        val locale = localeResolver.resolveLocale(request);
        val tokenOp = tokenParser.parse(new ServletWebRequest(request, response), locale);

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

        if (ac != null) {
            ac.authenticate(RestfulSecurityContext.getUserDetails().orElse(null), annotation);
        }

        return true;
    }

    private Method getMethod(Object handler) {
        return ((HandlerMethod) handler).getMethod();
    }

    // lazy
    private synchronized void initCache(Collection<HandlerMethod> handlerMethods) {
        if (!initialized) {

            final Map<Method, AuthenticationComponent> map1 = new HashMap<>();
            final Map<Method, Annotation> map2 = new HashMap<>();

            for (HandlerMethod hm : handlerMethods) {
                Method method = hm.getMethod();

                for (Annotation annotation : method.getDeclaredAnnotations()) {
                    Requires requires = annotation.annotationType().getAnnotation(Requires.class);
                    if (requires != null) {
                        map1.put(method, SpringUtils.getBean(requires.value()));
                        map2.put(method, annotation);
                        break;
                    }
                }
            }

            cache1 = Collections.unmodifiableMap(map1);
            cache2 = Collections.unmodifiableMap(map2);

            this.initialized = true;
        }
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

    public LocaleResolver getLocaleResolver() {
        return localeResolver;
    }

    public void setLocaleResolver(LocaleResolver localeResolver) {
        this.localeResolver = localeResolver;
    }

    public void setAuthenticationStrategy(AuthenticationStrategy authenticationStrategy) {
        this.authenticationStrategy = authenticationStrategy;
    }

    // ----------------------------------------------------------------------------------------------------------------

    private static class NullLocaleResolver implements LocaleResolver {

        @Override
        public Locale resolveLocale(HttpServletRequest request) {
            return null;
        }

        @Override
        public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {
            // 无动作
        }
    }
}
