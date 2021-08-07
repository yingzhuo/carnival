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
import com.github.yingzhuo.carnival.security.errorhandler.TokenAuthenticationEntryPoint;
import com.github.yingzhuo.carnival.security.token.resolver.BearerTokenResolver;
import com.github.yingzhuo.carnival.security.token.resolver.TokenResolver;
import com.github.yingzhuo.carnival.spring.BeanFinder;
import org.springframework.context.ApplicationContext;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 应卓
 * @since 1.10.2
 */
class CustomHttpSecurityDSL extends AbstractHttpConfigurer<CustomHttpSecurityDSL, HttpSecurity> {

    @Override
    public void configure(HttpSecurity http) throws Exception {

        final BeanFinder beanFinder = BeanFinder.newInstance(http.getSharedObject(ApplicationContext.class));

        final TokenResolver tokenResolver = getTokenResolver(beanFinder);
        final TokenAuthenticationManager authManager = getTokenAuthenticationManager(beanFinder);

        if (authManager != null) {
            final TokenAuthenticationEntryPoint entryPoint = beanFinder.getPrimaryQuietly(TokenAuthenticationEntryPoint.class).orElse(null);

            TokenAuthenticationFilter filter = new TokenAuthenticationFilter(tokenResolver, authManager);
            filter.setAuthenticationEntryPoint(entryPoint);

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

    private TokenResolver getTokenResolver(BeanFinder beanFinder) {
        List<TokenResolver> list = new ArrayList<>(beanFinder.getMultipleQuietly(TokenResolver.class));
        if (!list.isEmpty()) {
            return TokenResolver.builder()
                    .add(list)
                    .build();
        }
        return BearerTokenResolver.INSTANCE;
    }

    private TokenAuthenticationManager getTokenAuthenticationManager(BeanFinder beanFinder) {
        return beanFinder.getPrimaryQuietly(TokenAuthenticationManager.class).orElse(null);
    }

}
