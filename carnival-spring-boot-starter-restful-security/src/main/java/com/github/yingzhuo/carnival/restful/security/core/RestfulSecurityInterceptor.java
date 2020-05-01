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

import com.github.yingzhuo.carnival.common.mvc.interceptor.HandlerInterceptorSupport;
import com.github.yingzhuo.carnival.restful.security.AuthenticationStrategy;
import com.github.yingzhuo.carnival.restful.security.annotation.AuthenticationComponent;
import com.github.yingzhuo.carnival.restful.security.annotation.IgnoreToken;
import com.github.yingzhuo.carnival.restful.security.blacklist.TokenBlacklistManager;
import com.github.yingzhuo.carnival.restful.security.exception.TokenBlacklistedException;
import com.github.yingzhuo.carnival.restful.security.parser.TokenParser;
import com.github.yingzhuo.carnival.restful.security.realm.UserDetailsRealm;
import com.github.yingzhuo.carnival.restful.security.realm.x.ExtraUserDetailsRealm;
import com.github.yingzhuo.carnival.restful.security.token.Token;
import com.github.yingzhuo.carnival.restful.security.userdetails.UserDetails;
import lombok.val;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Optional;

/**
 * @author 应卓
 */
public class RestfulSecurityInterceptor extends HandlerInterceptorSupport {

    private TokenParser tokenParser;
    private UserDetailsRealm userDetailsRealm;
    private AuthenticationStrategy authenticationStrategy = AuthenticationStrategy.ANNOTATED_REQUESTS;
    private TokenBlacklistManager tokenBlacklistManager;
    private ExtraUserDetailsRealm extraUserDetailsRealm;

    @Override
    @SuppressWarnings("unchecked")
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        RestfulSecurityContext.clean();

        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        val servletWebRequest = new ServletWebRequest(request, response);

        val handlerMethod = (HandlerMethod) handler;

        if (hasMethodAnnotation(IgnoreToken.class, handler) || hasClassAnnotation(IgnoreToken.class, handler)) {
            return true;
        }

        final List<MethodCheckPoint> list = ReflectCache.get().get(handlerMethod.getMethod());
        if ((list == null || list.isEmpty()) && authenticationStrategy == AuthenticationStrategy.ANNOTATED_REQUESTS) {
            return true;
        }

        val tokenOp = tokenParser.parse(servletWebRequest);

        if (tokenOp.isPresent()) {

            Token token = tokenOp.get();
            RestfulSecurityContext.setToken(token);

            if (tokenBlacklistManager != null && tokenBlacklistManager.isBlacklisted(token)) {
                throw new TokenBlacklistedException(token);
            }

            Optional<UserDetails> userDetailsOp = userDetailsRealm.loadUserDetails(tokenOp.get());
            RestfulSecurityContext.setUserDetails(userDetailsOp.orElse(null));
        }

        if (list != null) {

            list.forEach(cp -> {
                Annotation annotation = cp.getAnnotation();
                AuthenticationComponent ac = cp.getAuthenticationComponent();
                ac.authenticate(RestfulSecurityContext.getToken().orElse(null), RestfulSecurityContext.getUserDetails().orElse(null), annotation);
            });
        }

        if (extraUserDetailsRealm != null) {
            extraUserDetailsRealm.execute(
                    RestfulSecurityContext.getToken().orElse(null),
                    RestfulSecurityContext.getUserDetails().orElse(null)
            );
        }

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

    public void setAuthenticationStrategy(AuthenticationStrategy authenticationStrategy) {
        this.authenticationStrategy = authenticationStrategy;
    }

    public void setTokenBlacklistManager(TokenBlacklistManager tokenBlacklistManager) {
        this.tokenBlacklistManager = tokenBlacklistManager;
    }

    public void setExtraUserDetailsRealm(ExtraUserDetailsRealm extraUserDetailsRealm) {
        this.extraUserDetailsRealm = extraUserDetailsRealm;
    }

}
