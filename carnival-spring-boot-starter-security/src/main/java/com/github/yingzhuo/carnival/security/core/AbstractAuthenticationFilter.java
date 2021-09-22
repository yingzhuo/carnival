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

import com.github.yingzhuo.carnival.security.token.Token;
import com.github.yingzhuo.carnival.security.token.resolver.TokenResolver;
import org.springframework.core.log.LogMessage;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 应卓
 * @since 1.10.23
 */
abstract class AbstractAuthenticationFilter extends OncePerRequestFilter {

    protected final TokenResolver tokenResolver;
    protected final AuthenticationProvider authenticationProvider;
    protected RememberMeServices rememberMeServices;
    protected AuthenticationEntryPoint authenticationEntryPoint;

    public AbstractAuthenticationFilter(TokenResolver tokenResolver, AuthenticationProvider authenticationProvider) {
        this.tokenResolver = tokenResolver;
        this.authenticationProvider = authenticationProvider;
    }

    @Override
    protected final void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (!authenticationIsRequired()) {
            filterChain.doFilter(request, response);
            return;
        }

        Token token = tokenResolver.resolve(new ServletWebRequest(request, response)).orElse(null);
        if (token == null) {
            filterChain.doFilter(request, response);
            return;
        }

        if (authenticationProvider.supports(token.getClass())) {
            try {
                Authentication authenticatedToken = authenticationProvider.authenticate(token);

                if (authenticatedToken == null) {
                    filterChain.doFilter(request, response);
                    return;
                }

                authenticatedToken.setAuthenticated(true);

                if (authenticatedToken instanceof AbstractAuthenticationToken) {
                    ((AbstractAuthenticationToken) authenticatedToken).setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                }

                SecurityContextHolder.getContext().setAuthentication(authenticatedToken);
                if (this.logger.isDebugEnabled()) {
                    this.logger.debug(LogMessage.format("Set SecurityContextHolder to %s", authenticatedToken));
                }

                if (rememberMeServices != null) {
                    rememberMeServices.loginSuccess(request, response, authenticatedToken);
                }

                onSuccessfulAuthentication(request, response, authenticatedToken);
            } catch (AuthenticationException e) {
                SecurityContextHolder.clearContext();

                if (rememberMeServices != null) {
                    rememberMeServices.loginFail(request, response);
                }

                onUnsuccessfulAuthentication(request, response, e);

                if (authenticationEntryPoint != null) {
                    authenticationEntryPoint.commence(request, response, e);
                    return;
                }
            }
        }

        filterChain.doFilter(request, response);
    }

    protected final boolean authenticationIsRequired() {
        final Authentication existingAuth = SecurityContextHolder.getContext().getAuthentication();
        if (existingAuth == null || !existingAuth.isAuthenticated()) {
            return true;
        }
        return (existingAuth instanceof AnonymousAuthenticationToken);
    }

    protected void onSuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, Authentication authResult) {
        // nop
    }

    protected void onUnsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {
        // nop
    }

    public void setRememberMeServices(RememberMeServices rememberMeServices) {
        this.rememberMeServices = rememberMeServices;
    }

    public void setAuthenticationEntryPoint(AuthenticationEntryPoint authenticationEntryPoint) {
        this.authenticationEntryPoint = authenticationEntryPoint;
    }

}
