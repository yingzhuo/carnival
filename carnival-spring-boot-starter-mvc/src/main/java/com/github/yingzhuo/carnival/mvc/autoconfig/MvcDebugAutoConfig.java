/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.mvc.autoconfig;

import com.github.yingzhuo.carnival.common.autoconfig.support.AnnotationAttributesHolder;
import com.github.yingzhuo.carnival.common.condition.ConditionalOnDebugMode;
import com.github.yingzhuo.carnival.mvc.EnableMvcDebugLogging;
import com.github.yingzhuo.carnival.mvc.MvcDebugLoggingImpl;
import com.github.yingzhuo.carnival.mvc.support.DebugMvcFilter;
import com.github.yingzhuo.carnival.mvc.support.DebugMvcInterceptor;
import com.github.yingzhuo.carnival.mvc.support.NOPFilter;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.Filter;
import java.util.UUID;

/**
 * @author 应卓
 */
@Slf4j
@ConditionalOnDebugMode
@ConditionalOnWebApplication
public class MvcDebugAutoConfig implements WebMvcConfigurer, InitializingBean {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        MvcDebugLoggingImpl impl = AnnotationAttributesHolder.getValue(EnableMvcDebugLogging.class, "impl");

        if (impl == MvcDebugLoggingImpl.INTERCEPTOR) {
            registry.addInterceptor(new DebugMvcInterceptor()).addPathPatterns("/", "/**").order(Ordered.LOWEST_PRECEDENCE);
        }
    }

    @Bean
    public FilterRegistrationBean<Filter> debugMvcFilterFilterRegistrationBean() {

        MvcDebugLoggingImpl impl = AnnotationAttributesHolder.getValue(EnableMvcDebugLogging.class, "impl");
        if (impl == MvcDebugLoggingImpl.FILTER) {
            val bean = new FilterRegistrationBean<Filter>(new DebugMvcFilter());
            bean.addUrlPatterns("/*");
            bean.setName(DebugMvcFilter.class.getName());
            bean.setOrder(Ordered.LOWEST_PRECEDENCE);
            return bean;
        } else {
            val bean = new FilterRegistrationBean<Filter>(new NOPFilter());
            bean.addUrlPatterns("/nop/" + UUID.randomUUID().toString());
            bean.setName(NOPFilter.class.getName());
            bean.setOrder(Ordered.LOWEST_PRECEDENCE);
            return bean;
        }
    }

    @Override
    public void afterPropertiesSet() {
        log.warn("Spring MVC debug-plugin enabled! DO NOT use this plugin in production environment.");
    }

}
