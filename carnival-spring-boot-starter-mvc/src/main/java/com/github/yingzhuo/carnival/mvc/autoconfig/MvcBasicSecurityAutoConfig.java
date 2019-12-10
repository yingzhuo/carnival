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

import com.github.yingzhuo.carnival.mvc.autoconfig.filter.HttpBasicSecurityFilter;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;

import java.util.Map;

/**
 * @author 应卓
 * @since 1.3.5
 */
@ConditionalOnWebApplication
@ConditionalOnProperty(prefix = "carnival.mvc.http-basic", name = "enabled", havingValue = "true")
@EnableConfigurationProperties(MvcBasicSecurityAutoConfig.Props.class)
public class MvcBasicSecurityAutoConfig {

    @Autowired(required = false)
    private HttpBasicSecurityFilter.AccessDeniedHandler accessDeniedHandler;

    @Bean
    @Autowired(required = false)
    public FilterRegistrationBean<HttpBasicSecurityFilter> httpBasicSecurityFilter(Props props) {
        final FilterRegistrationBean<HttpBasicSecurityFilter> bean = new FilterRegistrationBean<>();
        bean.setFilter(new HttpBasicSecurityFilter(accessDeniedHandler, props.getUsernameAndPassword()));
        bean.setName(HttpBasicSecurityFilter.class.getName());
        bean.setOrder(props.getOrder());
        bean.addUrlPatterns(props.getUrlPattern());
        return bean;
    }

    @Getter
    @Setter
    @ConfigurationProperties("carnival.mvc.http-basic")
    static class Props {
        private boolean enabled = false;
        private String[] urlPattern;
        private Map<String, String> usernameAndPassword;
        private int order = Ordered.HIGHEST_PRECEDENCE;
    }

}
