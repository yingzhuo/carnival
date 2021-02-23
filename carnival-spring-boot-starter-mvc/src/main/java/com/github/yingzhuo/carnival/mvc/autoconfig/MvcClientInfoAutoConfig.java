/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.mvc.autoconfig;

import com.github.yingzhuo.carnival.mvc.client.*;
import com.github.yingzhuo.carnival.mvc.props.AbstractWebFilterProps;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.MethodParameter;
import org.springframework.core.Ordered;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

import static com.github.yingzhuo.carnival.mvc.ClientInfo.*;

/**
 * @author 应卓
 * @since 1.6.14
 */
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
@EnableConfigurationProperties(MvcClientInfoAutoConfig.Props.class)
@ConditionalOnProperty(prefix = "carnival.web-filter.client-info", name = "enabled", havingValue = "true")
public class MvcClientInfoAutoConfig implements WebMvcConfigurer {

    @Autowired(required = false)
    private ClientOSTypeResolver clientOSTypeResolver;

    @Autowired(required = false)
    private ClientOSVersionResolver clientOSVersionResolver;

    @Autowired(required = false)
    private ClientAppVersionResolver clientAppVersionResolver;

    @Autowired(required = false)
    private ClientUsingBackendVersionResolver clientUsingBackendVersionResolver;

    @Bean
    public FilterRegistrationBean<ClientInfoResolvingFilter> clientOSTypeResolvingFilterFilter(Props props) {
        final FilterRegistrationBean<ClientInfoResolvingFilter> bean = new FilterRegistrationBean<>();
        final ClientInfoResolvingFilter filter = new ClientInfoResolvingFilter(
                clientOSTypeResolver != null ? clientOSTypeResolver : ClientInfoResolver.DEFAULT,
                clientOSVersionResolver != null ? clientOSVersionResolver : ClientInfoResolver.DEFAULT,
                clientAppVersionResolver != null ? clientAppVersionResolver : ClientInfoResolver.DEFAULT,
                clientUsingBackendVersionResolver != null ? clientUsingBackendVersionResolver : ClientInfoResolver.DEFAULT
        );
        bean.setFilter(filter);
        bean.setOrder(props.getOrder());
        bean.addUrlPatterns(props.getUrlPatterns());
        bean.setName(props.getFilterName());
        return bean;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new ClientOSTypeArgumentResolver());
        resolvers.add(new ClientOSVersionArgumentResolver());
        resolvers.add(new ClientVersionArgumentResolver());
        resolvers.add(new ClientUsingBackendVersionArgumentResolver());
    }

    @Getter
    @Setter
    @ConfigurationProperties(prefix = "carnival.web-filter.client-info")
    static class Props extends AbstractWebFilterProps {
        private boolean enabled = false;

        Props() {
            super.setOrder(Ordered.LOWEST_PRECEDENCE);
            super.setFilterName(ClientInfoResolvingFilter.class.getName());
        }
    }

    private static class ClientOSTypeArgumentResolver implements HandlerMethodArgumentResolver {
        @Override
        public boolean supportsParameter(MethodParameter parameter) {
            return parameter.hasParameterAnnotation(OSType.class) || parameter.getParameterType() == ClientOSType.class;
        }

        @Override
        public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
            return ClientInfoContext.getClientOSType();
        }
    }

    private static class ClientOSVersionArgumentResolver implements HandlerMethodArgumentResolver {
        @Override
        public boolean supportsParameter(MethodParameter parameter) {
            return parameter.hasParameterAnnotation(OSVersion.class) || parameter.getParameterType() == String.class;
        }

        @Override
        public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
            return ClientInfoContext.getClientOSVersion();
        }
    }

    private static class ClientUsingBackendVersionArgumentResolver implements HandlerMethodArgumentResolver {
        @Override
        public boolean supportsParameter(MethodParameter parameter) {
            return parameter.hasParameterAnnotation(UsingBackendVersion.class) || parameter.getParameterType() == String.class;
        }

        @Override
        public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
            return ClientInfoContext.getClientUsingBackendVersion();
        }
    }

    private static class ClientVersionArgumentResolver implements HandlerMethodArgumentResolver {
        @Override
        public boolean supportsParameter(MethodParameter parameter) {
            return parameter.hasParameterAnnotation(AppVersion.class) || parameter.getParameterType() == String.class;
        }

        @Override
        public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
            return ClientInfoContext.getClientAppVersion();
        }
    }

}
