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

import com.github.yingzhuo.carnival.restful.flow.Prev;
import com.github.yingzhuo.carnival.restful.flow.core.RequestFlowContext;
import com.github.yingzhuo.carnival.restful.flow.core.RequestFlowCoreInterceptor;
import com.github.yingzhuo.carnival.restful.flow.parser.StepTokenParser;
import com.github.yingzhuo.carnival.restful.flow.props.FlowProps;
import com.github.yingzhuo.carnival.restful.flow.signature.AlgorithmGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

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

    @Autowired
    private AlgorithmGenerator algorithmGenerator;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new RequestFlowCoreInterceptor(algorithmGenerator.create(), parser))
                .addPathPatterns("/", "/**")
                .order(props.getInterceptor().getOrder());
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new RequestFlowHandlerMethodArgumentResolver());
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * @since 1.6.1
     */
    private static class RequestFlowHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {
        @Override
        public boolean supportsParameter(MethodParameter parameter) {
            return parameter.getParameterType() == Prev.class;
        }

        @Override
        public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
            return new Prev(RequestFlowContext.getName(), RequestFlowContext.getStep());
        }
    }

}
