/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.mvc.log;

import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author 应卓
 * @since 1.9.14
 */
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
class LogFilterAutoConfiguration implements WebMvcConfigurer {

    static int defaultOrder = Ordered.HIGHEST_PRECEDENCE + 200;
    static String[] defaultUrlPattern = new String[0];
    static String[] defaultSkipAntPattern = new String[0];

    @Bean
    FilterRegistrationBean<LogFilter> logFilterFilterRegistrationBean() {
        FilterRegistrationBean<LogFilter> bean = new FilterRegistrationBean<>();
        LogFilter logFilter = new LogFilter();
        logFilter.setSkipPatterns(defaultSkipAntPattern);
        bean.setFilter(logFilter);
        bean.setName(LogFilter.class.getName());
        bean.setOrder(defaultOrder);
        bean.addUrlPatterns(defaultUrlPattern);
        return bean;
    }

}
