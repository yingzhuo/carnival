/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.kubernetes.health;

import com.github.yingzhuo.carnival.common.autoconfig.support.AnnotationAttributesHolder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.core.Ordered;
import org.springframework.core.type.AnnotationMetadata;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.lang.annotation.*;

/**
 * @author 应卓
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import(EnableHealthFilter.ImportSelector.class)
public @interface EnableHealthFilter {

    public String[] paths = {
            "/health",
            "/healthz"
    };

    class ImportSelector implements org.springframework.context.annotation.ImportSelector {
        @Override
        public String[] selectImports(AnnotationMetadata importingClassMetadata) {
            AnnotationAttributesHolder.setAnnotationMetadata(EnableHealthFilter.class, importingClassMetadata);
            return new String[]{HealthFilterAutoConfig.class.getName()};
        }
    }

    @ConditionalOnWebApplication
    class HealthFilterAutoConfig {

        @Bean
        @ConditionalOnMissingBean
        public FilterRegistrationBean<HealthFilter> healthFilterFilterRegistrationBean() {
            String[] paths = AnnotationAttributesHolder.getValue(EnableHealthFilter.class, "paths");

            if (paths == null || paths.length == 0) {
                paths = new String[]{
                        "/health",
                        "/healthz"
                };
            }

            FilterRegistrationBean<HealthFilter> bean = new FilterRegistrationBean<>(new HealthFilter());
            bean.addUrlPatterns(paths);
            bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
            return bean;
        }

    }

    static class HealthFilter implements Filter {
        @Override
        public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) {
            // response status -> 200
        }
    }

}
