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

import com.github.yingzhuo.carnival.common.mvc.AbstractHandlerInterceptorSupport;
import com.github.yingzhuo.carnival.restful.security.AuthenticationStrategy;
import com.github.yingzhuo.carnival.restful.security.blacklist.TokenBlacklistManager;
import com.github.yingzhuo.carnival.restful.security.parser.TokenParser;
import com.github.yingzhuo.carnival.restful.security.realm.UserDetailsRealm;
import com.github.yingzhuo.carnival.restful.security.whitelist.TokenWhitelistManager;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 应卓
 */
public class RestfulSecurityInterceptor extends AbstractHandlerInterceptorSupport implements RestfulSecurity {

    private AuthenticationStrategy authenticationStrategy;
    private TokenParser tokenParser;
    private UserDetailsRealm userDetailsRealm;
    private TokenBlacklistManager tokenBlacklistManager;
    private TokenWhitelistManager tokenWhitelistManager;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        RestfulSecurityContext.clean();
        RestfulSecurityContext.setRequest(request);

        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        doExecute(request, response, (HandlerMethod) handler);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        RestfulSecurityContext.clean();
    }

    @Override
    public AuthenticationStrategy getAuthenticationStrategy() {
        return authenticationStrategy;
    }

    public void setAuthenticationStrategy(AuthenticationStrategy authenticationStrategy) {
        this.authenticationStrategy = authenticationStrategy;
    }

    @Override
    public TokenParser getTokenParser() {
        return tokenParser;
    }

    public void setTokenParser(TokenParser tokenParser) {
        this.tokenParser = tokenParser;
    }

    @Override
    public UserDetailsRealm getUserDetailsRealm() {
        return userDetailsRealm;
    }

    public void setUserDetailsRealm(UserDetailsRealm userDetailsRealm) {
        this.userDetailsRealm = userDetailsRealm;
    }

    @Override
    public TokenBlacklistManager getTokenBlacklistManager() {
        return tokenBlacklistManager;
    }

    public void setTokenBlacklistManager(TokenBlacklistManager tokenBlacklistManager) {
        this.tokenBlacklistManager = tokenBlacklistManager;
    }

    @Override
    public TokenWhitelistManager getTokenWhitelistManager() {
        return tokenWhitelistManager;
    }

    public void setTokenWhitelistManager(TokenWhitelistManager tokenWhitelistManager) {
        this.tokenWhitelistManager = tokenWhitelistManager;
    }

}
