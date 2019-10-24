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
import com.github.yingzhuo.carnival.restful.security.blacklist.TokenBlacklistManager;
import com.github.yingzhuo.carnival.restful.security.cache.CacheManager;
import com.github.yingzhuo.carnival.restful.security.exception.TokenBlacklistedException;
import com.github.yingzhuo.carnival.restful.security.hook.AfterHook;
import com.github.yingzhuo.carnival.restful.security.hook.BeforeHook;
import com.github.yingzhuo.carnival.restful.security.parser.TokenParser;
import com.github.yingzhuo.carnival.restful.security.realm.UserDetailsRealm;
import com.github.yingzhuo.carnival.restful.security.token.Token;
import com.github.yingzhuo.carnival.restful.security.userdetails.UserDetails;
import lombok.val;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Optional;

/**
 * @author 应卓
 */
@SuppressWarnings("unchecked")
public class RestfulSecurityInterceptor implements HandlerInterceptor {

    private TokenParser tokenParser;
    private UserDetailsRealm userDetailsRealm;
    private CacheManager cacheManager;
    private AuthenticationStrategy authenticationStrategy = AuthenticationStrategy.ONLY_ANNOTATED;
    private TokenBlacklistManager tokenBlacklistManager;
    private BeforeHook beforeHook;
    private AfterHook afterHook;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        RestfulSecurityContext.clean();

        if (ReflectCache.isNotInitialized()) {
            ReflectCache.init();
        }

        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        val servletWebRequest = new ServletWebRequest(request, response);

        val handlerMethod = (HandlerMethod) handler;

        final List<MethodCheckPoint> list = ReflectCache.get().get(handlerMethod.getMethod());
        if ((list == null || list.isEmpty()) && authenticationStrategy == AuthenticationStrategy.ONLY_ANNOTATED) {
            return true;
        }

        beforeHook.execute(servletWebRequest);

        val tokenOp = tokenParser.parse(servletWebRequest);

        if (tokenOp.isPresent()) {

            Token token = tokenOp.get();
            RestfulSecurityContext.setToken(token);

            if (tokenBlacklistManager.isBlacklisted(token)) {
                throw new TokenBlacklistedException();
            }

            Optional<UserDetails> userDetailsOp;
            Optional<UserDetails> cached = cacheManager.getUserDetails(token);

            if (cached.isPresent()) {
                userDetailsOp = cached;
            } else {
                userDetailsOp = userDetailsRealm.loadUserDetails(tokenOp.get());
                userDetailsOp.ifPresent(ud -> cacheManager.saveUserDetails(token, ud));
            }

            RestfulSecurityContext.setUserDetails(userDetailsOp.orElse(null));
        }

        if (list != null) {

            list.forEach(cp -> {
                Annotation annotation = cp.getAnnotation();
                AuthenticationComponent ac = cp.getAuthenticationComponent();

                ac.authenticate(
                        RestfulSecurityContext.getToken().orElse(null),
                        RestfulSecurityContext.getUserDetails().orElse(null),
                        annotation);
            });

        }

        afterHook.execute(
                servletWebRequest,
                RestfulSecurityContext.getToken().orElse(null),
                RestfulSecurityContext.getUserDetails().orElse(null)
        );

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        RestfulSecurityContext.clean();
    }

    private Method getMethod(Object handler) {
        return ((HandlerMethod) handler).getMethod();
    }

    public void setTokenParser(TokenParser tokenParser) {
        this.tokenParser = tokenParser;
    }

    public void setUserDetailsRealm(UserDetailsRealm userDetailsRealm) {
        this.userDetailsRealm = userDetailsRealm;
    }

    public void setCacheManager(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    public void setAuthenticationStrategy(AuthenticationStrategy authenticationStrategy) {
        this.authenticationStrategy = authenticationStrategy;
    }

    public void setTokenBlacklistManager(TokenBlacklistManager tokenBlacklistManager) {
        this.tokenBlacklistManager = tokenBlacklistManager;
    }

    public void setBeforeHook(BeforeHook beforeHook) {
        this.beforeHook = beforeHook;
    }

    public void setAfterHook(AfterHook afterHook) {
        this.afterHook = afterHook;
    }
}