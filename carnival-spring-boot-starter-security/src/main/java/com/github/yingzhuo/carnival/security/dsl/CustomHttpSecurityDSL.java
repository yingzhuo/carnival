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
import com.github.yingzhuo.carnival.security.token.resolver.TokenResolver;
import com.github.yingzhuo.carnival.spring.BeanFinder;
import org.springframework.context.ApplicationContext;
import org.springframework.core.Ordered;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.util.Optional;

/**
 * @author 应卓
 * @since 1.10.2
 */
class CustomHttpSecurityDSL extends AbstractHttpConfigurer<CustomHttpSecurityDSL, HttpSecurity> implements Ordered {

    @Override
    public void configure(HttpSecurity http) {

        final BeanFinder beanFinder = BeanFinder.newInstance(http.getSharedObject(ApplicationContext.class));
        Optional<TokenResolver> tokenResolverOption = beanFinder.getOneOrPrimaryQuietly(TokenResolver.class);
        Optional<TokenAuthenticationManager> tokenAuthenticationManagerOption = beanFinder.getOneOrPrimaryQuietly(TokenAuthenticationManager.class);

        if (tokenResolverOption.isPresent() && tokenAuthenticationManagerOption.isPresent()) {
            final TokenAuthenticationFilter filter = new TokenAuthenticationFilter(
                    tokenResolverOption.get(),
                    tokenAuthenticationManagerOption.get()
            );

            http.setSharedObject(TokenAuthenticationFilter.class, filter);
            http.addFilterAfter(filter, BasicAuthenticationFilter.class);
        }
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }

}
