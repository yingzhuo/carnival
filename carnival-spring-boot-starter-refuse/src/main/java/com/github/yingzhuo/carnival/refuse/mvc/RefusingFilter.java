/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.refuse.mvc;

import com.github.yingzhuo.carnival.refuse.*;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

/**
 * @author 应卓
 */
public class RefusingFilter extends OncePerRequestFilter {

    private final PathMatcher pathMatcher = new AntPathMatcher();
    private RefusingConfigLoader loader;
    private RefusingListener listener;

    public RefusingFilter(RefusingConfigLoader loader, RefusingListener listener) {
        this.loader = loader;
        this.listener = listener;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        RefusingConfig refusingConfig = loader.load();
        Map<String, String> config = refusingConfig.toMap();
        String path = request.getRequestURI();

        config.keySet().forEach(pattern -> {
            if (pathMatcher.match(pattern, path)) {
                String reason = config.get(pattern);
                if (!StringUtils.hasText(reason)) {
                    reason = refusingConfig.getDefaultReason();
                }
                listener.execute(new RefuseContext(new Date(), path, reason));
                throw new AccessRefusedException(reason);
            }
        });

        filterChain.doFilter(request, response);
    }

}
