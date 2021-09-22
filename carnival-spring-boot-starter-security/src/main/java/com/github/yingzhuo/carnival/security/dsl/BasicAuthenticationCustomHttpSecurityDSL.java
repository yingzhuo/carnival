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

import com.github.yingzhuo.carnival.security.core.BasicAuthenticationFilter;
import com.github.yingzhuo.carnival.security.core.BasicAuthenticationFilterFactory;
import com.github.yingzhuo.carnival.spring.BeanFinder;
import org.springframework.context.ApplicationContext;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

/**
 * @author 应卓
 * @see BasicAuthenticationFilter
 * @see BasicAuthenticationFilterFactory
 * @since 1.10.22
 */
class BasicAuthenticationCustomHttpSecurityDSL extends AbstractHttpConfigurer<BasicAuthenticationCustomHttpSecurityDSL, HttpSecurity> {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        final BeanFinder beanFinder = BeanFinder.newInstance(http.getSharedObject(ApplicationContext.class));

        BasicAuthenticationFilter filter = this.getBasicAuthenticationFilter(beanFinder);

        if (filter != null) {
//            http.httpBasic().disable();

            filter.afterPropertiesSet();
            http.setSharedObject(BasicAuthenticationFilter.class, filter);
            http.addFilterBefore(filter, org.springframework.security.web.authentication.www.BasicAuthenticationFilter.class);
        }
    }

    private BasicAuthenticationFilter getBasicAuthenticationFilter(BeanFinder beanFinder) {
        final BasicAuthenticationFilterFactory factory = beanFinder.getPrimaryQuietly(BasicAuthenticationFilterFactory.class).orElse(null);
        return factory == null ? null : factory.create();
    }

}
