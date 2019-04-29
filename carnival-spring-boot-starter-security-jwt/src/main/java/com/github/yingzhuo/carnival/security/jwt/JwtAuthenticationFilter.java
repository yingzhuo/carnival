/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.security.jwt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.Assert;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;
import java.util.Optional;

/**
 * @author 应卓
 */
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenParser tokenParser;
    private final AbstractJwtAuthenticationManager authManager;
    private final JwtAuthenticationFailedEntryPoint authenticationEntryPoint;

    public JwtAuthenticationFilter(JwtTokenParser tokenParser, AbstractJwtAuthenticationManager authManager, JwtAuthenticationFailedEntryPoint entryPoint) {
        this.tokenParser = tokenParser;
        this.authManager = authManager;
        this.authenticationEntryPoint = entryPoint;
    }

    @Override
    public void afterPropertiesSet() throws ServletException {
        super.afterPropertiesSet();
        Assert.notNull(tokenParser, () -> null);
        Assert.notNull(authManager, () -> null);
        Assert.notNull(authenticationEntryPoint, () -> null);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        log.info("进入Filter");

        Optional<JwtToken> tokenOption = tokenParser.parse(request, Locale.getDefault());

        try {
            Authentication authResult = authManager.authenticate(tokenOption.orElse(null));
            SecurityContextHolder.getContext().setAuthentication(authResult);
            log.debug("认证成功");

        } catch (AuthenticationException failed) {

            log.debug("认证失败");
            SecurityContextHolder.clearContext();
            authenticationEntryPoint.commence(request, response, failed);
            return;
        }

        filterChain.doFilter(request, response);
    }

}
