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

import lombok.Getter;
import lombok.Setter;
import lombok.val;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author 应卓
 */
@ConditionalOnWebApplication
@EnableConfigurationProperties(MvcCorsForAllAutoConfig.Props.class)
@ConditionalOnProperty(prefix = "carnival.mvc.cors-for-all", name = "enabled")
public class MvcCorsForAllAutoConfig implements WebMvcConfigurer {

    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilterRegistrationBean() {
        val bean = new FilterRegistrationBean<CorsFilter>();
        bean.setFilter(corsFilter());
        bean.addUrlPatterns("/", "/*");
        bean.setName(CorsFilter.class.getSimpleName());
        return bean;
    }

    private CorsFilter corsFilter() {
        val cnf = new CorsConfiguration();
        cnf.addAllowedOrigin("*"); // 1 设置访问源地址
        cnf.addAllowedHeader("*"); // 2 设置访问源请求头
        cnf.addAllowedMethod("*"); // 3 设置访问源请求方法

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", cnf); // 4 对接口配置跨域设置
        return new CorsFilter(source);
    }

    @Getter
    @Setter
    @ConfigurationProperties(prefix = "carnival.mvc.cors-for-all")
    static class Props {
        private boolean enabled = false;
    }

}
