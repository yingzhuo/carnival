/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.document.core;

import com.github.yingzhuo.carnival.common.io.ResourceText;
import com.github.yingzhuo.carnival.document.util.MarkdownEntityFactory;
import org.springframework.core.io.Resource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 应卓
 * @since 1.6.11
 */
public class DocumentPageFilter extends OncePerRequestFilter {

    private final String rootPath;
    private final Charset charset;
    private final Map<String, String> subPathToHtml = new HashMap<>();


    public DocumentPageFilter(String rootPath, Map<String, Resource> resourceMap, Charset charset) {
        this.rootPath = rootPath;
        this.charset = charset;

        for (String subPath : resourceMap.keySet()) {
            final String markdown = ResourceText.of(resourceMap.get(subPath), charset).getText();
            final String html = MarkdownEntityFactory.create(markdown).asHtml();
            subPathToHtml.put(subPath, html);
        }
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String fullPath = request.getRequestURI();
        String subPath = fullPath.substring(rootPath.length());

        if (subPath.endsWith(".html")) {
            subPath = subPath.substring(0, subPath.length() - 5);   // 去掉后缀
        }

        final String html = subPathToHtml.get(subPath);
        if (html != null) {
            response.setContentType("text/html;charset=" + charset.toString());
            response.setStatus(200);

            response.getWriter().println(html);
            response.getWriter().flush();
            return;
        }

        filterChain.doFilter(request, response);
    }

}
