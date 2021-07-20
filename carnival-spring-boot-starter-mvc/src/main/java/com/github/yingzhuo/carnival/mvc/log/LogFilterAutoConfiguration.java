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

    @Bean
    FilterRegistrationBean<LogFilter> logFilterFilterRegistrationBean() {
        FilterRegistrationBean<LogFilter> bean = new FilterRegistrationBean<>();
        bean.setFilter(new LogFilter());
        bean.setName(LogFilter.class.getName());
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE + 200);
        bean.addUrlPatterns("/*");
        return bean;
    }

}
