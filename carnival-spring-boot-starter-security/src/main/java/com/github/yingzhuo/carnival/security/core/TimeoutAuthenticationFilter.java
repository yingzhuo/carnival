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

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author 应卓
 * @since 1.10.25
 */
public class TimeoutAuthenticationFilter extends OncePerRequestFilter {

    private final long timeout;

    public TimeoutAuthenticationFilter(String timeout) throws ParseException {
        this(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(timeout));
    }

    public TimeoutAuthenticationFilter(Date timeout) {
        this(timeout.getTime());
    }

    public TimeoutAuthenticationFilter(long timeout) {
        this.timeout = timeout;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {

        final Authentication authentication = getCurrentAuthentication();
        if (timeout > 0 && authentication != null && timeout > System.currentTimeMillis()) {
            SecurityContextHolder.clearContext();
            Authentication newOne = new MagicAuthentication(authentication);
            SecurityContextHolder.getContext().setAuthentication(newOne);
        }

        chain.doFilter(request, response);
    }

    private Authentication getCurrentAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    private static final class MagicAuthentication implements Authentication {
        private final Authentication auth;

        public MagicAuthentication(Authentication auth) {
            this.auth = Objects.requireNonNull(auth);
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            final List<GrantedAuthority> list = new ArrayList<>(auth.getAuthorities());
            list.add(() -> "ROLE_NOT_TIMEOUT");
            return list;
        }

        @Override
        public Object getCredentials() {
            return auth.getCredentials();
        }

        @Override
        public Object getDetails() {
            return auth.getDetails();
        }

        @Override
        public Object getPrincipal() {
            return auth.getPrincipal();
        }

        @Override
        public boolean isAuthenticated() {
            return auth.isAuthenticated();
        }

        @Override
        public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
            auth.setAuthenticated(isAuthenticated);
        }

        @Override
        public String getName() {
            return auth.getName();
        }
    }
}
