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

import com.github.yingzhuo.carnival.restful.flow.parser.CompositeStepTokenParser;
import com.github.yingzhuo.carnival.restful.flow.parser.HttpHeaderStepTokenParser;
import com.github.yingzhuo.carnival.restful.flow.parser.HttpParameterStepTokenParser;
import com.github.yingzhuo.carnival.restful.flow.parser.StepTokenParser;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpHeaders;

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
    public StepTokenParser stepTokenParser() {
        return new CompositeStepTokenParser(
                new HttpParameterStepTokenParser("_flow_token"),
                new HttpParameterStepTokenParser("_step_token"),
                new HttpHeaderStepTokenParser(HttpHeaders.AUTHORIZATION, "Bearer ")
        );
    }

}
