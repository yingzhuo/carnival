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

import com.github.yingzhuo.carnival.exception.ExceptionTransformer;
import com.github.yingzhuo.carnival.restful.flow.RestfulFlowConfigurer;
import com.github.yingzhuo.carnival.restful.flow.core.DefaultRequestFlowBean;
import com.github.yingzhuo.carnival.restful.flow.core.RequestFlowBean;
import com.github.yingzhuo.carnival.restful.flow.core.RequestFlowCoreInterceptor;
import com.github.yingzhuo.carnival.restful.flow.parser.FlowTokenParser;
import com.github.yingzhuo.carnival.restful.flow.signature.AlgorithmGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.Duration;

/**
 * @author 应卓
 * @since 1.3.6
 */
@ConditionalOnWebApplication
public class RequestFlowCoreAutoConfig implements WebMvcConfigurer {

    @Autowired(required = false)
    private FlowTokenParser parser;

    @Autowired(required = false)
    private AlgorithmGenerator algorithmGenerator;

    @Autowired(required = false)
    private ExceptionTransformer exceptionTransformer;

    @Autowired(required = false)
    private RestfulFlowConfigurer configurer = new RestfulFlowConfigurer() {
    };

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        final RequestFlowCoreInterceptor interceptor = new RequestFlowCoreInterceptor();
        interceptor.setAlgorithm(getAlgorithmGenerator().create());
        interceptor.setTokenParser(getFlowTokenParser());
        interceptor.setExceptionTransformer(getExceptionTransformer());

        registry.addInterceptor(interceptor)
                .addPathPatterns(getPathPatterns())
                .order(getOrder());
    }

    @Bean
    @ConditionalOnMissingBean
    public RequestFlowBean requestFlowBean() {
        return new DefaultRequestFlowBean(
                getAlgorithmGenerator().create(),
                getTokenTimeToLive()
        );
    }

    private int getOrder() {
        return configurer.getOrder();
    }

    private FlowTokenParser getFlowTokenParser() {
        if (parser != null) return parser;
        return configurer.getFlowTokenParser();
    }

    private AlgorithmGenerator getAlgorithmGenerator() {
        if (algorithmGenerator != null) return algorithmGenerator;
        return configurer.getAlgorithmGenerator();
    }

    private Duration getTokenTimeToLive() {
        return configurer.getTokenTimeToLive();
    }

    private ExceptionTransformer getExceptionTransformer() {
        if (exceptionTransformer != null) {
            return exceptionTransformer;
        }
        return configurer.getExceptionTransformer();
    }

    private String[] getPathPatterns() {
        return configurer != null ? configurer.getPathPatterns() : new String[]{"/", "/**"};
    }

}
