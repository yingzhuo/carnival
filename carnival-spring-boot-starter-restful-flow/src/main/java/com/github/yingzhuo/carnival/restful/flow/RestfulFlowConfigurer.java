/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.restful.flow;

import com.auth0.jwt.algorithms.Algorithm;
import com.github.yingzhuo.carnival.common.mvc.MvcInterceptorConfigurer;
import com.github.yingzhuo.carnival.exception.ExceptionTransformer;
import com.github.yingzhuo.carnival.restful.flow.parser.FlowTokenParser;
import com.github.yingzhuo.carnival.restful.flow.parser.HttpParameterFlowTokenParser;
import com.github.yingzhuo.carnival.restful.flow.signature.AlgorithmGenerator;
import com.github.yingzhuo.carnival.restful.flow.signature.AlgorithmGenerators;
import org.springframework.core.Ordered;

import java.time.Duration;

/**
 * @author 应卓
 * @since 1.6.6
 */
public interface RestfulFlowConfigurer extends MvcInterceptorConfigurer {

    @Override
    public default int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE + 900;
    }

    public default FlowTokenParser getFlowTokenParser() {
        return new HttpParameterFlowTokenParser("_flow_token");
    }

    public default Duration getTokenTimeToLive() {
        return Duration.ofMinutes(5);
    }

    public default AlgorithmGenerator getAlgorithmGenerator() {
        return AlgorithmGenerators.hmac512(Algorithm.class.getName());
    }

    public default ExceptionTransformer getExceptionTransformer() {
        return null;
    }

}
