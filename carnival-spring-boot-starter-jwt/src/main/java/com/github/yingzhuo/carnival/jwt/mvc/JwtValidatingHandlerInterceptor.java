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

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.github.yingzhuo.carnival.jwt.JwtTokenParser;
import com.github.yingzhuo.carnival.jwt.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * @author 应卓
 */
@Slf4j
public class JwtValidatingHandlerInterceptor extends HandlerInterceptorAdapter {

    private final SignatureAlgorithm signatureAlgorithm;
    private final String secret;
    private final Set<String> excludePatterns;
    private final PathMatcher pathMatcher = new AntPathMatcher();
    private final JwtTokenParser jwtTokenParser;

    public JwtValidatingHandlerInterceptor(SignatureAlgorithm signatureAlgorithm, String secret, Set<String> excludePatterns, JwtTokenParser jwtTokenParser) {
        if (excludePatterns != null) {
            this.excludePatterns = excludePatterns;
        } else {
            this.excludePatterns = new HashSet<>(0);
        }

        this.signatureAlgorithm = signatureAlgorithm;
        this.secret = secret;
        this.jwtTokenParser = jwtTokenParser;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        final String path = request.getRequestURI();

        for (String excludePattern : excludePatterns) {
            boolean skip = pathMatcher.match(excludePattern, path);
            if (skip) {
                return true;
            }
        }

        Optional<String> tokenOp = jwtTokenParser.parse(new ServletWebRequest(request));

        if (!tokenOp.isPresent()) {
            throw new RuntimeException("无法解析Token");
        }

        Algorithm algorithm = Algorithm.HMAC256(this.secret);
        JWTVerifier verifier = JWT.require(algorithm).build();

        try {
            verifier.verify(tokenOp.get());
        } catch (JWTVerificationException e) {
            throw new RuntimeException(e.getMessage());
        }

        return true;
    }

    private Method getMethod(Object handler) {
        return ((HandlerMethod) handler).getMethod();
    }

}
