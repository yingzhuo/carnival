/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.security.dsl;

import com.github.yingzhuo.carnival.security.authentication.BasicAuthenticationProvider;
import com.github.yingzhuo.carnival.security.core.BasicAuthenticationFilter;
import com.github.yingzhuo.carnival.security.core.BasicAuthenticationFilterFactory;
import com.github.yingzhuo.carnival.spring.BeanFinder;
import org.springframework.context.ApplicationContext;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.RememberMeServices;

/**
 * @author 应卓
 * @see BasicAuthenticationFilter
 * @see BasicAuthenticationFilterFactory
 * @see BasicAuthenticationProvider
 * @since 1.10.22
 */
class BasicAuthenticationCustomHttpSecurityDSL extends AbstractHttpConfigurer<BasicAuthenticationCustomHttpSecurityDSL, HttpSecurity> {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        final BeanFinder finder = BeanFinder.newInstance(http.getSharedObject(ApplicationContext.class));

        final BasicAuthenticationFilter filter = this.getBasicAuthenticationFilter(finder);

        if (filter != null) {
            filter.setRememberMeServices(getRememberMeServices(finder));
            filter.afterPropertiesSet();

            http.setSharedObject(BasicAuthenticationFilter.class, filter);
            http.addFilterBefore(filter, org.springframework.security.web.authentication.www.BasicAuthenticationFilter.class);
        }
    }

    private BasicAuthenticationFilter getBasicAuthenticationFilter(BeanFinder finder) {
        final BasicAuthenticationFilterFactory factory = finder.getPrimaryQuietly(BasicAuthenticationFilterFactory.class).orElse(null);
        if (factory != null) {
            return factory.create();
        }

        final BasicAuthenticationProvider provider = finder.getPrimaryQuietly(BasicAuthenticationProvider.class).orElse(null);
        return provider != null ? new BasicAuthenticationFilter(provider) : null;
    }

    private RememberMeServices getRememberMeServices(BeanFinder finder) {
        return finder.getPrimaryQuietly(RememberMeServices.class).orElse(null);
    }

}
