/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.jwt.mvc;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

/**
 * @author 应卓
 */
@Slf4j
public class JwtValidatingHandlerInterceptor extends HandlerInterceptorAdapter {

    private final PathMatcher pathMatcher = new AntPathMatcher();
    private final Set<String> excludePatterns;

    public JwtValidatingHandlerInterceptor(Set<String> excludePatterns) {
        if (excludePatterns != null) {
            this.excludePatterns = excludePatterns;
        } else {
            this.excludePatterns = new HashSet<>(0);
        }
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        final String path = request.getRequestURI();

        for (String excludePattern : excludePatterns) {
            boolean skip = pathMatcher.match(excludePattern, path);
            if (skip) {
                break;
            } else {
                // JWT权限验证
            }
        }

        return true;
    }

    private Method getMethod(Object handler) {
        return ((HandlerMethod) handler).getMethod();
    }

}
