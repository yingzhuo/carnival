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
import com.github.yingzhuo.carnival.restful.flow.props.JwtProps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author 应卓
 * @since 1.3.6
 */
@ConditionalOnWebApplication
@ConditionalOnProperty(prefix = "carnival.flow", name = "enabled", havingValue = "true", matchIfMissing = true)
@EnableConfigurationProperties(JwtProps.class)
@AutoConfigureAfter(RequestFlowBeanAutoConfig.class)
public class RequestFlowAutoConfig implements WebMvcConfigurer {

    @Autowired
    private JwtProps jwtprops;

    @Autowired
    private StepTokenParser stepTokenParser;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new RequestFlowCoreInterceptor(jwtprops.getAlgorithm(), stepTokenParser)).addPathPatterns("/", "/**");
    }

}
