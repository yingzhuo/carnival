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
@ConditionalOnProperty(prefix = "carnival.mvc.client-info", name = "enabled", havingValue = "true", matchIfMissing = true)
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
    public FilterRegistrationBean<ClientInfoResolvingFilter> clientOSTypeResolvingFilterFilter() {
        FilterRegistrationBean<ClientInfoResolvingFilter> bean = new FilterRegistrationBean<>();
        bean.setFilter(new ClientInfoResolvingFilter(
                clientOSTypeResolver != null ? clientOSTypeResolver : ClientInfoResolver.DEFAULT,
                clientOSVersionResolver != null ? clientOSVersionResolver : ClientInfoResolver.DEFAULT,
                clientAppVersionResolver != null ? clientAppVersionResolver : ClientInfoResolver.DEFAULT,
                clientUsingBackendVersionResolver != null ? clientUsingBackendVersionResolver : ClientInfoResolver.DEFAULT
        ));

        bean.setOrder(Ordered.HIGHEST_PRECEDENCE + 1000);
        bean.addUrlPatterns("/*");
        bean.setName(ClientInfoResolvingFilter.class.getSimpleName());
        return bean;
    }

    @Getter
    @Setter
    @ConfigurationProperties(prefix = "carnival.mvc.client-info")
    static class Props {
        private boolean enabled = true;
    }

}
