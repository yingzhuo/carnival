/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.apigateway.autoconfig;

import com.github.yingzhuo.carnival.apigateway.filter.HttpBasicHttpZuulFilter;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.util.Assert;

/**
 * @author 应卓
 */
@ConditionalOnProperty(prefix = "carnival.api-gateway.http-basic", name = "enabled", havingValue = "true")
@EnableConfigurationProperties(HttpBasicZuulFilterAutoConfig.Props.class)
public class HttpBasicZuulFilterAutoConfig {

    @Bean
    @ConditionalOnMissingBean
    public HttpBasicHttpZuulFilter httpBasicHttpHeaderSetterZuulFilter(Props props) {
        return new HttpBasicHttpZuulFilter(props.getUsername(), props.getPassword());
    }

    @Getter
    @Setter
    @ConfigurationProperties(prefix = "carnival.api-gateway.http-basic")
    static final class Props implements InitializingBean {
        private boolean enabled = false;
        private String username;
        private String password;

        @Override
        public void afterPropertiesSet() {
            if (enabled) {
                Assert.hasText(username, "'carnival.api-gateway.http-basic.username' is null or blank.");
                Assert.hasText(password, "'carnival.api-gateway.http-basic.password' is null or blank.");
            }
        }
    }

}
