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

import com.github.yingzhuo.carnival.security.core.LoggingFilter;
import com.github.yingzhuo.carnival.security.core.LoggingFilterFactory;
import com.github.yingzhuo.carnival.spring.BeanFinder;
import org.springframework.context.ApplicationContext;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

/**
 * @author 应卓
 * @since 1.10.10
 */
class LoggingCustomHttpSecurityDSL extends AbstractHttpConfigurer<LoggingCustomHttpSecurityDSL, HttpSecurity> {

    @Override
    public void configure(HttpSecurity http) {
        final BeanFinder beanFinder = BeanFinder.newInstance(http.getSharedObject(ApplicationContext.class));

        final LoggingFilter filter = getLoggingFilter(beanFinder);
        if (filter != null) {
            http.addFilterBefore(filter, BasicAuthenticationFilter.class);
            http.setSharedObject(LoggingFilter.class, filter);
        }
    }

    private LoggingFilter getLoggingFilter(BeanFinder beanFinder) {
        LoggingFilterFactory factory = beanFinder.getPrimaryQuietly(LoggingFilterFactory.class).orElse(null);
        return factory != null ?
                factory.create() :
                null;
    }

}
