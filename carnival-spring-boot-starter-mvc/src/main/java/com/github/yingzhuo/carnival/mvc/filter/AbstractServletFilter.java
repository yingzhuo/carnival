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

import com.github.yingzhuo.carnival.spring.JacksonUtils;
import com.github.yingzhuo.carnival.spring.PathMatcherUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 应卓
 * @since 1.6.13
 */
public abstract class AbstractServletFilter extends OncePerRequestFilter {

    protected static final String UTF_8 = "UTF-8";

    private String[] skipPatterns;  // setter设置

    @Override
    protected final void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        final String path = request.getRequestURI();

        if (skipPatterns != null && skipPatterns.length > 0 && PathMatcherUtils.anyMatch(path, skipPatterns)) {
            filterChain.doFilter(request, response);
            return;
        }

        if (doFilter(request, response)) {
            filterChain.doFilter(request, response);
        }
    }

    protected abstract boolean doFilter(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;

    protected final void writeJson(HttpServletResponse response, Object obj) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding(UTF_8);
        response.getWriter().print(JacksonUtils.writeValueAsString(obj));
        response.getWriter().flush();
    }

    protected final void writeText(HttpServletResponse response, String text) throws IOException {
        response.setContentType("text/plain");
        response.setCharacterEncoding(UTF_8);
        response.getWriter().print(text);
        response.getWriter().flush();
    }

    protected final void writeHtml(HttpServletResponse response, String html) throws IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding(UTF_8);
        response.getWriter().print(html);
        response.getWriter().flush();
    }

    public final void setSkipPatterns(String... skipPatters) {
        this.skipPatterns = skipPatters;
    }

}
