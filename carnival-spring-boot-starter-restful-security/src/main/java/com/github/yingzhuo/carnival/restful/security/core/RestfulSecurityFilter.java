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
import com.github.yingzhuo.carnival.restful.security.blacklist.TokenBlacklistManager;
import com.github.yingzhuo.carnival.restful.security.exception.RestfulSecurityException;
import com.github.yingzhuo.carnival.restful.security.exceptionhandler.ExceptionHandler;
import com.github.yingzhuo.carnival.restful.security.parser.TokenParser;
import com.github.yingzhuo.carnival.restful.security.realm.UserDetailsRealm;
import com.github.yingzhuo.carnival.restful.security.whitelist.TokenWhitelistManager;
import com.github.yingzhuo.carnival.spring.RequestMappingUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 应卓
 * @since 1.6.22
 */
public class RestfulSecurityFilter extends OncePerRequestFilter implements RestfulSecurity {

    private AuthenticationStrategy authenticationStrategy;
    private TokenParser tokenParser;
    private UserDetailsRealm userDetailsRealm;
    private TokenBlacklistManager tokenBlacklistManager;
    private TokenWhitelistManager tokenWhitelistManager;
    private ExceptionHandler exceptionHandler;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        RestfulSecurityContext.clean();
        RestfulSecurityContext.setRequest(request);

        try {
            doExecute(request, response, RequestMappingUtils.getHandlerMethod(request));
        } catch (Exception e) {
            if (e instanceof RestfulSecurityException) {
                exceptionHandler.handler(request, response, (RestfulSecurityException) e);
            } else {
                exceptionHandler.handler(request, response, e);
            }
            RestfulSecurityContext.clean();
            return;
        }

        chain.doFilter(request, response);
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

    public ExceptionHandler getExceptionHandler() {
        return exceptionHandler;
    }

    public void setExceptionHandler(ExceptionHandler exceptionHandler) {
        this.exceptionHandler = exceptionHandler;
    }

}
