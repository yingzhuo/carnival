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

import com.github.yingzhuo.carnival.security.core.TimeoutAuthenticationFilter;
import com.github.yingzhuo.carnival.security.core.TimeoutAuthenticationFilterFactory;
import com.github.yingzhuo.carnival.spring.BeanFinder;
import org.springframework.context.ApplicationContext;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestFilter;

/**
 * @author 应卓
 * @since 1.10.25
 */
class TimeoutAuthenticationCustomHttpSecurityDSL extends AbstractHttpConfigurer<TimeoutAuthenticationCustomHttpSecurityDSL, HttpSecurity> {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        final BeanFinder finder = new BeanFinder(http.getSharedObject(ApplicationContext.class));
        final TimeoutAuthenticationFilterFactory factory = finder.getPrimaryQuietly(TimeoutAuthenticationFilterFactory.class).orElse(null);
        if (factory != null) {
            TimeoutAuthenticationFilter filter = factory.create();
            if (filter != null) {
                filter.afterPropertiesSet();
                http.addFilterBefore(filter, SecurityContextHolderAwareRequestFilter.class);
                http.setSharedObject(TimeoutAuthenticationFilter.class, filter);
            }
        }
    }

}
