/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.restful.flow.autoconfig;

import com.github.yingzhuo.carnival.restful.flow.parser.HttpParameterStepTokenParser;
import com.github.yingzhuo.carnival.restful.flow.parser.StepTokenParser;
import com.github.yingzhuo.carnival.restful.flow.signature.AlgorithmGenerator;
import com.github.yingzhuo.carnival.restful.flow.signature.AlgorithmGenerators;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;

/**
 * @author 应卓
 * @since 1.3.6
 */
@Lazy(false)
@ConditionalOnWebApplication
@ConditionalOnProperty(prefix = "carnival.flow", name = "enabled", havingValue = "true", matchIfMissing = true)
public class RequestFlowBeanAutoConfig {

    @Bean
    @ConditionalOnMissingBean
    public AlgorithmGenerator algorithmGenerator() {
        return AlgorithmGenerators.hmac512(AlgorithmGenerator.class.getName());
    }

    @Bean
    @ConditionalOnMissingBean
    public StepTokenParser stepTokenParser() {
        return new HttpParameterStepTokenParser("_flow_token");
    }

}
