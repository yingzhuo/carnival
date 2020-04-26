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
import com.github.yingzhuo.carnival.common.mvc.interceptor.HandlerInterceptorSupport;
import com.github.yingzhuo.carnival.restful.flow.RequestFlow;
import com.github.yingzhuo.carnival.restful.flow.exception.RequestFlowException;
import com.github.yingzhuo.carnival.restful.flow.parser.StepTokenParser;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class RequestFlowCoreInterceptor extends HandlerInterceptorSupport {

    private Algorithm algorithm;
    private StepTokenParser stepTokenParser;

    public RequestFlowCoreInterceptor(Algorithm algorithm, StepTokenParser stepTokenParser) {
        this.algorithm = algorithm;
        this.stepTokenParser = stepTokenParser;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        final RequestFlow annotation = super.getMethodAnnotation(RequestFlow.class, handler).orElse(null);
        if (annotation == null) {
            return true;
        }

        if (annotation.prevStep().length == 0) {
            return true;
        }

        final String stepToken = stepTokenParser.parse(new ServletWebRequest(request, response)).orElse(null);
        if (stepToken == null) {
            throw new RequestFlowException();
        }

        try {
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(stepToken);
            final String nameInToken = jwt.getClaim("name").asString();
            final Integer stepInToken = jwt.getClaim("step").asInt();

            Set<Integer> prevSet = new HashSet<>();
            for (int i : annotation.prevStep()) {
                prevSet.add(i);
            }

            if (!Objects.equals(nameInToken, annotation.name()) || !prevSet.contains(stepInToken)) {
                throw new RequestFlowException();
            }

        } catch (AlgorithmMismatchException | TokenExpiredException | SignatureVerificationException | InvalidClaimException | JWTDecodeException ex) {
            log.warn(ex.getMessage(), ex);
            throw new RequestFlowException(ex.getMessage());
        }

        return true;
    }

}
