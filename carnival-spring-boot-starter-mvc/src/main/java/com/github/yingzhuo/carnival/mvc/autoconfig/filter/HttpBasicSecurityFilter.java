/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.mvc.autoconfig.filter;

import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author 应卓
 * @since 1.3.5
 */
public class HttpBasicSecurityFilter extends OncePerRequestFilter {

    private static final String AUTHORIZATION = "Authorization";
    private static final String BASIC = "Basic ";

    private final AccessDeniedHandler accessDeniedHandler;
    private final Map<String, String> usernameAndPasswordMap = new HashMap<>();

    public HttpBasicSecurityFilter() {
        this(null, null);
    }

    public HttpBasicSecurityFilter(AccessDeniedHandler accessDeniedHandler, Map<String, String> usernameAndPasswordMap) {
        this.accessDeniedHandler = accessDeniedHandler != null ? accessDeniedHandler : response -> {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.getWriter().write("Access Denied");
            response.getWriter().flush();
        };

        if (usernameAndPasswordMap != null) {
            this.usernameAndPasswordMap.putAll(usernameAndPasswordMap);
        }
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        final String header = request.getHeader(AUTHORIZATION);

        if (header == null) {
            accessDeniedHandler.handle(response);
            return;
        }

        if (!header.startsWith(BASIC)) {
            accessDeniedHandler.handle(response);
            return;
        }

        String usernameAndPassword = header.substring(BASIC.length());
        usernameAndPassword = new String(Base64.getUrlDecoder().decode(usernameAndPassword.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);

        final String[] up = usernameAndPassword.split(":", 2);

        if (up.length != 2) {
            accessDeniedHandler.handle(response);
            return;
        }

        String tokenUsername = up[0];
        String tokenPassword = up[1];

        if (!Objects.equals(tokenPassword, usernameAndPasswordMap.get(tokenUsername))) {
            accessDeniedHandler.handle(response);
            return;
        }

        filterChain.doFilter(request, response);
    }

    @FunctionalInterface
    public static interface AccessDeniedHandler {
        public void handle(HttpServletResponse response) throws ServletException, IOException;
    }

}
