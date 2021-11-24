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

import com.github.yingzhuo.carnival.security.core.CorsFilter;
import com.github.yingzhuo.carnival.security.core.CorsFilterFactory;
import com.github.yingzhuo.carnival.spring.BeanFinder;
import org.springframework.context.ApplicationContext;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

/**
 * @author 应卓
 * @author 朱嘉杰
 * @see CorsFilter
 * @see CorsFilterFactory
 * @since 1.10.38
 */
class CorsCustomHttpSecurityDSL extends AbstractHttpConfigurer<CorsCustomHttpSecurityDSL, HttpSecurity> {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        final BeanFinder finder = BeanFinder.newInstance(http.getSharedObject(ApplicationContext.class));

        final CorsFilter filter = getCorsFilter(finder);
        if (filter != null) {
            filter.afterPropertiesSet();
            http.addFilterAfter(filter, BasicAuthenticationFilter.class);
            http.setSharedObject(CorsFilter.class, filter);
        }
    }

    private CorsFilter getCorsFilter(BeanFinder finder) {
        CorsFilterFactory factory = finder.getPrimaryQuietly(CorsFilterFactory.class).orElse(null);
        return factory != null ?
                factory.create() :
                null;
    }

}
