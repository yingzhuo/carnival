/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.jwt.validating.mvc;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.github.yingzhuo.carnival.jwt.SignatureAlgorithm;
import com.github.yingzhuo.carnival.jwt.exception.*;
import com.github.yingzhuo.carnival.jwt.util.InternalUtls;
import com.github.yingzhuo.carnival.jwt.validating.AuthorizationManager;
import com.github.yingzhuo.carnival.jwt.validating.TokenParser;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * @author 应卓
 */
public class TokenValidatingInterceptor extends HandlerInterceptorAdapter {

    private String secret;
    private SignatureAlgorithm signatureAlgorithm;
    private TokenParser tokenParser;
    private AuthorizationManager authorizationManager;
    private Set<String> excludePatterns = new HashSet<>(0);
    private final PathMatcher pathMatcher = new AntPathMatcher();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        final String path = request.getRequestURI();

        if (excludePatterns.stream().anyMatch(pattern -> pathMatcher.match(pattern, path))) {
            return true;
        }

        WebRequest webRequest = new ServletWebRequest(request);
        Optional<String> tokenOp = tokenParser.parse(webRequest);

        if (!tokenOp.isPresent()) {
            throw new TokenNotFoundException();
        }

        final String token = tokenOp.get();
        Algorithm algorithm = InternalUtls.toAlgorithm(signatureAlgorithm, secret);
        JWTVerifier verifier = JWT.require(algorithm).build();

        try {
            DecodedJWT jwt = verifier.verify(token);
            authorizationManager.check(jwt, webRequest, ((HandlerMethod) handler).getMethod());
            JwtContext.setJwt(jwt);
        } catch (com.auth0.jwt.exceptions.AlgorithmMismatchException ex) {
            throw new AlgorithmMismatchException(ex.getMessage(), ex);
        } catch (com.auth0.jwt.exceptions.TokenExpiredException ex) {
            throw new TokenExpiredException(ex.getMessage(), ex);
        } catch (com.auth0.jwt.exceptions.SignatureVerificationException ex) {
            throw new SignatureVerificationException(ex.getMessage(), ex);
        } catch (com.auth0.jwt.exceptions.InvalidClaimException ex) {
            throw new InvalidClaimException(ex.getMessage(), ex);
        }

        return true;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public void setSignatureAlgorithm(SignatureAlgorithm signatureAlgorithm) {
        this.signatureAlgorithm = signatureAlgorithm;
    }

    public void setTokenParser(TokenParser tokenParser) {
        this.tokenParser = tokenParser;
    }

    public void setAuthorizationManager(AuthorizationManager authorizationManager) {
        this.authorizationManager = authorizationManager;
    }

    public void setExcludePatterns(Set<String> excludePatterns) {
        this.excludePatterns = excludePatterns;
    }

}
