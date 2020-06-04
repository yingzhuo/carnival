
package com.github.yingzhuo.carnival.mvc.autoconfig;

import com.github.yingzhuo.carnival.mvc.client.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
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

}
