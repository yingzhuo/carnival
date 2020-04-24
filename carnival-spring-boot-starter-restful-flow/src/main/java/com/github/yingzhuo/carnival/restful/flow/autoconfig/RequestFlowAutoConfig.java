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

import com.github.yingzhuo.carnival.restful.flow.core.RequestFlowCoreInterceptor;
import com.github.yingzhuo.carnival.restful.flow.parser.StepTokenParser;
import com.github.yingzhuo.carnival.restful.flow.props.FlowProps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.OrderComparator;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;
import java.util.Optional;

/**
 * @author 应卓
 * @since 1.3.6
 */
@Lazy(false)
@ConditionalOnWebApplication
@ConditionalOnProperty(prefix = "carnival.flow", name = "enabled", havingValue = "true", matchIfMissing = true)
@EnableConfigurationProperties(FlowProps.class)
@AutoConfigureAfter(RequestFlowBeanAutoConfig.class)
public class RequestFlowAutoConfig implements WebMvcConfigurer {

    @Autowired
    private FlowProps flowProps;

    @Autowired
    private List<StepTokenParser> stepTokenParsers;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        StepTokenParser p;

        if (stepTokenParsers.size() == 1) {
            p = stepTokenParsers.get(0);
        } else {
            OrderComparator.sort(stepTokenParsers);
            p = stepTokenParsers.stream().reduce(token -> Optional.empty(), StepTokenParser::or);
        }

        registry.addInterceptor(new RequestFlowCoreInterceptor(flowProps.calcAlgorithm(), p))
                .addPathPatterns(flowProps.getInterceptorPatterns());
    }

}
