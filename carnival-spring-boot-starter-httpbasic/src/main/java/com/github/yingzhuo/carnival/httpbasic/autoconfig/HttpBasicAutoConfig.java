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

import com.github.yingzhuo.carnival.httpbasic.autoconfig.filter.HttpBasicAuthFilter;
import com.github.yingzhuo.carnival.httpbasic.autoconfig.handler.HttpBasicAuthFailureHandler;
import com.github.yingzhuo.carnival.httpbasic.autoconfig.handler.JsonHttpBasicAuthFailureHandler;
import com.github.yingzhuo.carnival.httpbasic.autoconfig.handler.SimpleHttpBasicAuthFailureHandler;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;

import java.util.UUID;

/**
 * @author 应卓
 */
@Slf4j
@ConditionalOnWebApplication
@EnableConfigurationProperties(HttpBasicAutoConfig.Props.class)
@ConditionalOnProperty(prefix = "carnival.http-basic", name = "enabled", havingValue = "true", matchIfMissing = true)
public class HttpBasicAutoConfig {

    @Bean
    @ConditionalOnMissingBean
    public HttpBasicAuthFailureHandler httpBasicAuthFailureHandler() {
        try {
            ClassUtils.getClass("com.github.yingzhuo.carnival.json.Json");
            return new JsonHttpBasicAuthFailureHandler();
        } catch (ClassNotFoundException e) {
            return new SimpleHttpBasicAuthFailureHandler();
        }
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

    // -----------------------------------------------------------------------------------------------------------------

    @Getter
    @Setter
    @ConfigurationProperties("carnival.http-basic")
    static class Props implements InitializingBean {
        private boolean enabled = true;
        private String username = "admin";
        private String password = null;
        private String[] antPatterns = new String[]{"/", "/**"};

        @Override
        public void afterPropertiesSet() {
            if (StringUtils.isAnyBlank(username, password)) {
                password = UUID.randomUUID().toString();
                log.debug("Http-Basic username: \n\n\t\t{}\n", username);
                log.debug("Http-Basic password: \n\n\t\t{}\n", password);
            }
        }
    }

}
