/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.mvc.filter;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;
import java.util.Map;

/**
 * @author 应卓
 */
public class HttpBasicAuthFilter extends OncePerRequestFilter {

    private static final String AUTHORIZATION = "Authorization";
    private static final String BASIC = "Basic ";

    private final PathMatcher pathMatcher = new AntPathMatcher();
    private final HttpBasicAuthFailureHandler failureHandler;
    private final Map<String, String> users;
    private final String[] antPatterns;

    public HttpBasicAuthFilter(HttpBasicAuthFailureHandler failureHandler, Map<String, String> users, String[] antPatterns) {
        this.failureHandler = failureHandler;
        this.users = users;
        this.antPatterns = antPatterns;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {

        if (Arrays.stream(antPatterns).noneMatch(it -> pathMatcher.match(it, request.getRequestURI()))) {
            System.out.println("没有匹配到任何pattern");
            filterChain.doFilter(request, response);
            return;
        }

        if (users == null || users.isEmpty()) {
            failureHandler.handle(request, response);
            return;
        }

        final String header = request.getHeader(AUTHORIZATION);

        if (header == null) {
            failureHandler.handle(request, response);
            return;
        }

        if (!header.startsWith(BASIC)) {
            failureHandler.handle(request, response);
            return;
        }


        String usernameAndPassword = header.substring(BASIC.length());
        usernameAndPassword = new String(Base64.getUrlDecoder().decode(usernameAndPassword.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);

        final String[] up = usernameAndPassword.split(":");

        if (up.length != 2) {
            failureHandler.handle(request, response);
            return;
        }

        for (String username : users.keySet()) {
            String password = users.get(username);

            if (StringUtils.equals(username, up[0]) && StringUtils.equals(password, up[1])) {
                doFilter(request, response, filterChain);
                return;
            }
        }

        failureHandler.handle(request, response);
    }

}
