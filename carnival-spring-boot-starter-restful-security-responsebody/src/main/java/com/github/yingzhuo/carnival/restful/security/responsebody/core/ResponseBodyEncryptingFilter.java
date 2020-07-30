/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.restful.security.responsebody.core;

import com.github.yingzhuo.carnival.restful.security.responsebody.ResponseBodyEncryptingAlgorithm;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Set;

/**
 * @author 应卓
 * @since 1.6.30
 */
@Slf4j
@Setter
public class ResponseBodyEncryptingFilter extends OncePerRequestFilter {

    private final PathMatcher pathMatcher = new AntPathMatcher();
    private Charset charset;
    private ResponseBodyEncryptingAlgorithm algorithm;
    private boolean debugMode;
    private Set<String> excludeAntPatterns;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {

        final String path = request.getRequestURI();

        if (excludeAntPatterns != null && !excludeAntPatterns.isEmpty()) {
            boolean skip = excludeAntPatterns.stream().anyMatch(pattern -> pathMatcher.match(pattern, path));
            if (skip) {
                chain.doFilter(request, response);
                return;
            }
        }

        final ResponseBodyEncryptingResponse responseWrapper = new ResponseBodyEncryptingResponse(response);
        chain.doFilter(request, responseWrapper);

        String responseData = responseWrapper.getResponseBodyAsString();
        writeEncryptedContent(responseData, response);
    }

    private void writeEncryptedContent(String responseData, ServletResponse response) throws IOException {
        ServletOutputStream out = null;
        try {
            responseData = algorithm.encrypt(responseData);
            response.setContentLength(responseData.length());
            response.setCharacterEncoding(charset.displayName());
            out = response.getOutputStream();
            out.write(responseData.getBytes(charset));
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (out != null) {
                out.flush();
                out.close();
            }
        }
    }

    public void init() {
        if (debugMode) {
            this.setAlgorithm(new DebugModeAlgorithm(this.algorithm));
        }
    }

    // ----------------------------------------------------------------------------------------------------------------

    private static class DebugModeAlgorithm implements ResponseBodyEncryptingAlgorithm {
        private final ResponseBodyEncryptingAlgorithm algorithm;

        public DebugModeAlgorithm(ResponseBodyEncryptingAlgorithm algorithm) {
            this.algorithm = algorithm;
        }

        @Override
        public String encrypt(String body) {
            String hashed = algorithm.encrypt(body);
            log.warn("debug mode enabled! encrypted response body:");
            log.warn(hashed);
            return body;
        }
    }

}
