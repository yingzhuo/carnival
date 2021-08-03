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

import com.github.yingzhuo.carnival.security.authentication.TokenAuthenticationManager;
import com.github.yingzhuo.carnival.security.core.TokenAuthenticationFilter;
import com.github.yingzhuo.carnival.security.core.TokenAuthenticationFilterCustomizer;
import com.github.yingzhuo.carnival.security.token.resolver.BearerTokenResolver;
import com.github.yingzhuo.carnival.security.token.resolver.TokenResolver;
import com.github.yingzhuo.carnival.spring.BeanFinder;
import org.springframework.context.ApplicationContext;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

/**
 * @author 应卓
 * @since 1.10.2
 */
class CustomHttpSecurityDSL extends AbstractHttpConfigurer<CustomHttpSecurityDSL, HttpSecurity> {

    @Override
    public void configure(HttpSecurity http) throws Exception {

        final BeanFinder beanFinder = BeanFinder.newInstance(http.getSharedObject(ApplicationContext.class));

        final TokenResolver tokenResolver = beanFinder.getPrimaryQuietly(TokenResolver.class)
                .orElse(BearerTokenResolver.INSTANCE);

        final TokenAuthenticationManager authManager = beanFinder.getPrimaryQuietly(TokenAuthenticationManager.class)
                .orElse(null);

        if (authManager != null) {
            TokenAuthenticationFilter filter = new TokenAuthenticationFilter(tokenResolver, authManager);

            TokenAuthenticationFilterCustomizer customizer = beanFinder.getPrimaryQuietly(TokenAuthenticationFilterCustomizer.class)
                    .orElse(null);

            if (customizer != null) {
                filter = customizer.customize(filter);
            }

            if (filter != null) {
                filter.afterPropertiesSet();
                http.setSharedObject(TokenAuthenticationFilter.class, filter);
                http.addFilterAfter(filter, BasicAuthenticationFilter.class);
            }
        }
    }

}
