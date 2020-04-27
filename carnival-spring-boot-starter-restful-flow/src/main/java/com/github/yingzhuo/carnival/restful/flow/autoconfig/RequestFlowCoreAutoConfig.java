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

import com.auth0.jwt.algorithms.Algorithm;
import com.github.yingzhuo.carnival.restful.flow.core.RequestFlowCoreInterceptor;
import com.github.yingzhuo.carnival.restful.flow.parser.StepTokenParser;
import com.github.yingzhuo.carnival.restful.flow.props.FlowProps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author 应卓
 * @since 1.3.6
 */
@Lazy(false)
@ConditionalOnWebApplication
@ConditionalOnProperty(prefix = "carnival.flow", name = "enabled", havingValue = "true", matchIfMissing = true)
@EnableConfigurationProperties(FlowProps.class)
@AutoConfigureAfter(RequestFlowBeanAutoConfig.class)
public class RequestFlowCoreAutoConfig implements WebMvcConfigurer {

    @Autowired
    private FlowProps props;

    @Autowired
    private StepTokenParser parser;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new RequestFlowCoreInterceptor(Algorithm.HMAC512(props.getSecret()), parser))
                .addPathPatterns(props.getInterceptor().getPatterns())
                .order(props.getInterceptor().getOrder());
    }

}
