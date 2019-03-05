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

import com.github.yingzhuo.carnival.apigateway.filter.HttpBasicHttpHeaderSetterZuulFilter;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * @author 应卓
 */
@ConditionalOnProperty(prefix = "carnival.api-gateway.http-basic", name = "enabled", havingValue = "true")
@EnableConfigurationProperties(HttpBasicHttpHeaderSetterZuulFilterAutoConfig.Props.class)
public class HttpBasicHttpHeaderSetterZuulFilterAutoConfig {

    @Bean
    public HttpBasicHttpHeaderSetterZuulFilter httpBasicHttpHeaderSetterZuulFilter(Props props) {
        return new HttpBasicHttpHeaderSetterZuulFilter(props.getUsername(), props.getPassword());
    }

    @Getter
    @Setter
    @ConfigurationProperties(prefix = "carnival.api-gateway.http-basic")
    static final class Props {
        private boolean enabled = false;
        private String username;
        private String password;
    }

}
