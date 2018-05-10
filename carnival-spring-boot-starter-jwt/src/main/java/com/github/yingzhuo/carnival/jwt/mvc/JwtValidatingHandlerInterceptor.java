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
import com.auth0.jwt.interfaces.DecodedJWT;
import com.github.yingzhuo.carnival.jwt.AuthorizationManager;
import com.github.yingzhuo.carnival.jwt.JwtTokenParser;
import com.github.yingzhuo.carnival.jwt.SignatureAlgorithm;
import com.github.yingzhuo.carnival.jwt.exception.InvalidTokenException;
import com.github.yingzhuo.carnival.jwt.exception.JwtParsingException;
import com.github.yingzhuo.carnival.jwt.util.InternalUtls;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

/**
 * @author 应卓
 */
@Slf4j
public class JwtValidatingHandlerInterceptor extends HandlerInterceptorAdapter {

    private final PathMatcher pathMatcher = new AntPathMatcher();
    private final SignatureAlgorithm signatureAlgorithm;
    private final String secret;
    private final Set<String> excludePatterns;
    private final JwtTokenParser jwtTokenParser;
    private final AuthorizationManager authorizationManager;

    public JwtValidatingHandlerInterceptor(SignatureAlgorithm signatureAlgorithm,
                                           String secret,
                                           Set<String> excludePatterns,
                                           JwtTokenParser jwtTokenParser,
                                           AuthorizationManager authorizationManager) {
        if (excludePatterns != null) {
            this.excludePatterns = excludePatterns;
        } else {
            this.excludePatterns = Collections.emptySet();
        }

        this.signatureAlgorithm = signatureAlgorithm;
        this.secret = secret;
        this.jwtTokenParser = jwtTokenParser;
        this.authorizationManager = authorizationManager;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        final String path = request.getRequestURI();

        if (excludePatterns.stream().anyMatch(pattern -> pathMatcher.match(pattern, path))) {
            return true;
        }

        Optional<String> tokenOp = jwtTokenParser.parse(new ServletWebRequest(request));

        if (!tokenOp.isPresent()) {
            throw new JwtParsingException("Can not parse token.");
        }

        Algorithm algorithm = InternalUtls.toAlgorithm(signatureAlgorithm, secret);
        JWTVerifier verifier = JWT.require(algorithm).build();

        try {
            DecodedJWT djwt = verifier.verify(tokenOp.get());
            authorizationManager.check(djwt, path, getMethod(handler), handler);
            JwtValidatingContext.setJwt(djwt);
        } catch (JWTVerificationException e) {
            throw new InvalidTokenException(e.getMessage());
        }

        return true;
    }

    private Method getMethod(Object handler) {
        return ((HandlerMethod) handler).getMethod();
    }

}
