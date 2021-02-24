/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.restful.flow.core;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.*;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.github.yingzhuo.carnival.common.mvc.AbstractHandlerInterceptorSupport;
import com.github.yingzhuo.carnival.exception.ExceptionTransformer;
import com.github.yingzhuo.carnival.restful.flow.RequestFlow;
import com.github.yingzhuo.carnival.restful.flow.exception.RequestFlowException;
import com.github.yingzhuo.carnival.restful.flow.parser.FlowTokenParser;
import lombok.val;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * @author 应卓
 * @since 1.3.6
 */
public class RequestFlowCoreInterceptor extends AbstractHandlerInterceptorSupport {

    private Algorithm algorithm;
    private FlowTokenParser tokenParser;
    private ExceptionTransformer exceptionTransformer;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        try {
            return doPreHandle(request, response, handler);
        } catch (Exception e) {
            if (exceptionTransformer != null) {
                throw exceptionTransformer.transform(e).orElse(e);
            }
            throw e;
        }
    }

    public boolean doPreHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        val annotation = getMethodAnnotation(RequestFlow.class, handler).orElse(null);

        if (annotation == null) {
            return true;
        }

        if (annotation.prevStep().length == 0) {
            return true;
        }

        final String stepToken = tokenParser.parse(new ServletWebRequest(request, response)).orElse(null);
        if (stepToken == null) {
            throw new RequestFlowException(request);
        }

        try {
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(stepToken);
            final String nameInToken = jwt.getClaim("name").asString();
            final Integer stepInToken = jwt.getClaim("step").asInt();

            final Set<Integer> prevSet = new HashSet<>();
            for (int i : annotation.prevStep()) {
                prevSet.add(i);
            }

            if (!Objects.equals(nameInToken, annotation.name()) || !prevSet.contains(stepInToken)) {
                throw new RequestFlowException(request);
            }

        } catch (AlgorithmMismatchException | TokenExpiredException | SignatureVerificationException | InvalidClaimException | JWTDecodeException ex) {
            throw new RequestFlowException(request);
        }

        return true;
    }

    public void setAlgorithm(Algorithm algorithm) {
        this.algorithm = algorithm;
    }

    public void setTokenParser(FlowTokenParser tokenParser) {
        this.tokenParser = tokenParser;
    }

    public void setExceptionTransformer(ExceptionTransformer exceptionTransformer) {
        this.exceptionTransformer = exceptionTransformer;
    }

}
