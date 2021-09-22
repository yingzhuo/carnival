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

import com.github.yingzhuo.carnival.security.core.TokenAuthenticationFilter;
import com.github.yingzhuo.carnival.security.core.TokenAuthenticationFilterFactory;
import com.github.yingzhuo.carnival.security.errorhandler.TokenAuthenticationEntryPoint;
import com.github.yingzhuo.carnival.security.token.resolver.BearerTokenResolver;
import com.github.yingzhuo.carnival.security.token.resolver.TokenResolver;
import com.github.yingzhuo.carnival.spring.BeanFinder;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.NullRememberMeServices;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.util.List;

/**
 * @author 应卓
 * @see TokenAuthenticationFilter
 * @see TokenAuthenticationFilterFactory
 * @since 1.10.2
 */
class TokenAuthenticationCustomHttpSecurityDSL extends AbstractHttpConfigurer<TokenAuthenticationCustomHttpSecurityDSL, HttpSecurity> {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        final BeanFinder beanFinder = BeanFinder.newInstance(http.getSharedObject(ApplicationContext.class));
        TokenAuthenticationFilter filter = this.getTokenAuthenticationFilter(beanFinder);

        if (filter != null) {
            filter.afterPropertiesSet();
            http.setSharedObject(TokenAuthenticationFilter.class, filter);
            http.addFilterAfter(filter, BasicAuthenticationFilter.class);
        }
    }

    private TokenAuthenticationFilter getTokenAuthenticationFilter(BeanFinder beanFinder) throws Exception {
        final TokenAuthenticationFilterFactory factory = beanFinder.getPrimaryQuietly(TokenAuthenticationFilterFactory.class).orElse(null);
        if (factory != null) {
            return factory.create();
        }

        final TokenResolver tokenResolver = getTokenResolver(beanFinder);
        final List<AuthenticationProvider> providers = getAuthenticationProviders(beanFinder);
        final TokenAuthenticationEntryPoint entryPoint = beanFinder.getPrimaryQuietly(TokenAuthenticationEntryPoint.class).orElse(null);

        if (tokenResolver != null && !providers.isEmpty()) {
            final TokenAuthenticationFilter filter = new TokenAuthenticationFilter(tokenResolver, providers);
            filter.setAuthenticationEntryPoint(entryPoint);
            filter.setRememberMeServices(getRememberMeServices(beanFinder));
            filter.afterPropertiesSet();
            return filter;
        } else {
            return null;
        }
    }

    private TokenResolver getTokenResolver(BeanFinder beanFinder) {
        final List<TokenResolver> list = beanFinder.getMultipleQuietly(TokenResolver.class);
        if (!list.isEmpty()) {
            return TokenResolver.builder()
                    .add(list)
                    .build();
        }
        return BearerTokenResolver.INSTANCE; // 标准
    }

    private List<AuthenticationProvider> getAuthenticationProviders(BeanFinder beanFinder) {
        return beanFinder.getMultiple(AuthenticationProvider.class);
    }

    private RememberMeServices getRememberMeServices(BeanFinder beanFinder) {
        return beanFinder.getPrimaryQuietly(RememberMeServices.class).orElse(new NullRememberMeServices());
    }

}
