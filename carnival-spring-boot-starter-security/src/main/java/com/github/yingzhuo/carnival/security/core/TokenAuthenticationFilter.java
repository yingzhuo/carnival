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

import com.github.yingzhuo.carnival.security.authentication.TokenAuthenticationManager;
import com.github.yingzhuo.carnival.security.token.Token;
import com.github.yingzhuo.carnival.security.token.resolver.TokenResolver;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
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
import java.util.Objects;
import java.util.Optional;

/**
 * @author 应卓
 * @since 1.10.2
 */
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private final TokenResolver tokenResolver;
    private final TokenAuthenticationManager authenticationManager;
    private AuthenticationEntryPoint authenticationEntryPoint;
    private RememberMeServices rememberMeServices;

    public TokenAuthenticationFilter(TokenResolver tokenResolver, TokenAuthenticationManager authenticationManager) {
        this.tokenResolver = Objects.requireNonNull(tokenResolver);
        this.authenticationManager = Objects.requireNonNull(authenticationManager);
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain chain) throws ServletException, IOException {

        if (authenticationIsRequired()) {
            Optional<Token> tokenOption = tokenResolver.resolve(new ServletWebRequest(request, response));
            if (tokenOption.isPresent()) {
                try {
                    Authentication authentication = authenticationManager.authenticate(tokenOption.get());
                    authentication.setAuthenticated(true);

                    if (authentication instanceof AbstractAuthenticationToken) {
                        ((AbstractAuthenticationToken) authentication).setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    }

                    SecurityContextHolder.getContext().setAuthentication(authentication);

                    if (rememberMeServices != null) {
                        rememberMeServices.loginSuccess(request, response, authentication);
                    }

                    onSuccessfulAuthentication(request, response, authentication);

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

            chain.doFilter(request, response);
        }
    }

    public TokenResolver getTokenResolver() {
        return tokenResolver;
    }

    public TokenAuthenticationManager getAuthenticationManager() {
        return authenticationManager;
    }

    public AuthenticationEntryPoint getAuthenticationEntryPoint() {
        return authenticationEntryPoint;
    }

    public void setAuthenticationEntryPoint(AuthenticationEntryPoint authenticationEntryPoint) {
        this.authenticationEntryPoint = authenticationEntryPoint;
    }

    public RememberMeServices getRememberMeServices() {
        return rememberMeServices;
    }

    public void setRememberMeServices(RememberMeServices rememberMeServices) {
        this.rememberMeServices = rememberMeServices;
    }

    protected void onSuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              Authentication authResult) {
        // NOP
    }

    protected void onUnsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                                AuthenticationException failed) {
        // NOP
    }

    protected final boolean authenticationIsRequired() {
        final Authentication existingAuth = SecurityContextHolder.getContext().getAuthentication();
        if (existingAuth == null || !existingAuth.isAuthenticated()) {
            return true;
        }
        return (existingAuth instanceof AnonymousAuthenticationToken);
    }

}
