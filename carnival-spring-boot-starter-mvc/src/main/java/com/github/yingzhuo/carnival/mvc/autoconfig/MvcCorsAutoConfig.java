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

import com.github.yingzhuo.carnival.mvc.props.AbstractWebFilterProps;
import lombok.Getter;
import lombok.Setter;
import lombok.val;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.Ordered;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author 应卓
 */
@Lazy(false)
@ConditionalOnWebApplication
@EnableConfigurationProperties(MvcCorsAutoConfig.Props.class)
@ConditionalOnProperty(prefix = "carnival.web-filter.cors", name = "enabled", havingValue = "true")
public class MvcCorsAutoConfig implements WebMvcConfigurer {

    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilterRegistrationBean(Props props) {
        val bean = new FilterRegistrationBean<CorsFilter>();
        bean.setFilter(corsFilter());
        bean.addUrlPatterns(props.getUrlPatterns());
        bean.setName(props.getFilterName());
        bean.setOrder(props.getOrder());
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

    // -----------------------------------------------------------------------------------------------------------------

    @Getter
    @Setter
    @ConfigurationProperties(prefix = "carnival.web-filter.cors")
    static class Props extends AbstractWebFilterProps {
        private boolean enabled = false;

        Props() {
            super.setOrder(Ordered.LOWEST_PRECEDENCE - 100);
            super.setFilterName(CorsFilter.class.getName());
        }
    }

}
