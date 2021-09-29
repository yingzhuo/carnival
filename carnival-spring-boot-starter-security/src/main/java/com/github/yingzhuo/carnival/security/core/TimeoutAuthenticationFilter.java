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

import org.apache.commons.lang3.time.DateUtils;
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
import java.util.*;

/**
 * @author 应卓
 * @since 1.10.25
 */
public class TimeoutAuthenticationFilter extends OncePerRequestFilter {

    private static final String[] patterns = new String[]{
            "yyyy-MM-dd HH:mm:ss",
            "yyyy-MM-dd"
    };
    private final long timeout;
    private final String notTimeoutRoleName;

    public TimeoutAuthenticationFilter(String notTimeoutRoleName, String timeout) {
        this(notTimeoutRoleName, parseDate(timeout));
    }

    public TimeoutAuthenticationFilter(String notTimeoutRoleName, Date timeout) {
        this(notTimeoutRoleName, timeout.getTime());
    }

    public TimeoutAuthenticationFilter(String notTimeoutRoleName, long timeout) {
        this.timeout = timeout;
        this.notTimeoutRoleName = Objects.requireNonNull(notTimeoutRoleName);
    }

    private static Date parseDate(String timeout) {
        try {
            return DateUtils.parseDate(timeout, patterns);
        } catch (ParseException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {

        final Authentication authentication = getCurrentAuthentication();
        if (timeout > 0 && authentication != null && timeout > System.currentTimeMillis()) {
            SecurityContextHolder.clearContext();
            Authentication newOne = new MagicAuthentication(authentication, notTimeoutRoleName);
            SecurityContextHolder.getContext().setAuthentication(newOne);
        }

        chain.doFilter(request, response);
    }

    private Authentication getCurrentAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    private static final class MagicAuthentication implements Authentication {
        private final Authentication auth;
        private final String notTimeoutRoleName;

        public MagicAuthentication(Authentication auth, String notTimeoutRoleName) {
            this.auth = auth;
            this.notTimeoutRoleName = notTimeoutRoleName;
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            final List<GrantedAuthority> list = new ArrayList<>(auth.getAuthorities());
            list.add(() -> notTimeoutRoleName);
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
