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
import org.springframework.context.annotation.Lazy;
import org.springframework.core.Ordered;

/**
 * @author 应卓
 * @since 1.6.14
 */
@Lazy(false)
@ConditionalOnWebApplication
@EnableConfigurationProperties(MvcClientInfoAutoConfig.Props.class)
@ConditionalOnProperty(prefix = "carnival.web-filter.client-info", name = "enabled", havingValue = "true")
public class MvcClientInfoAutoConfig {

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

}
