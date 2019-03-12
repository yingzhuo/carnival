/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.httpbasic.autoconfig;

import com.github.yingzhuo.carnival.httpbasic.autoconfig.filter.HttpBasicAuthFailureHandler;
import com.github.yingzhuo.carnival.httpbasic.autoconfig.filter.HttpBasicAuthFilter;
import lombok.Getter;
import lombok.Setter;
import lombok.val;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;

/**
 * @author 应卓
 */
@ConditionalOnWebApplication
@EnableConfigurationProperties(HttpBasicAutoConfig.Props.class)
@ConditionalOnProperty(prefix = "carnival.http-basic", name = "enabled", havingValue = "true")
public class HttpBasicAutoConfig {

    @Bean
    @ConditionalOnMissingBean
    public HttpBasicAuthFailureHandler httpBasicAuthFailureHandler() {
        return new HttpBasicAuthFailureHandler.DefaultHttpBasicAuthFailureHandler();
    }

    @Bean
    public FilterRegistrationBean<HttpBasicAuthFilter> httpBasicAuthFilterFilterRegistrationBean(Props props, HttpBasicAuthFailureHandler failureHandler) {
        val filter = new HttpBasicAuthFilter(failureHandler, props.getUsername(), props.getPassword(), props.getAntPatterns());

        val bean = new FilterRegistrationBean<HttpBasicAuthFilter>();
        bean.setEnabled(props.isEnabled());
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        bean.setName(HttpBasicAuthFilter.class.getSimpleName());
        bean.setFilter(filter);
        bean.addUrlPatterns("/*");
        return bean;
    }

    @Getter
    @Setter
    @ConfigurationProperties("carnival.http-basic")
    static class Props {
        private boolean enabled = false;
        private String username = "admin";
        private String password = "admin";
        private String[] antPatterns = new String[]{"/", "/**"};
    }

}
