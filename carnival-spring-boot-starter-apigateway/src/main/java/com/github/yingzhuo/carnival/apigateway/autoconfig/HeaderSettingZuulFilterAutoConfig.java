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

import com.github.yingzhuo.carnival.apigateway.filter.HeaderSettingZuulFilter;
import lombok.Getter;
import lombok.Setter;
import lombok.val;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 应卓
 */
@ConditionalOnProperty(prefix = "carnival.api-gateway.header-setting", name = "enabled", havingValue = "true")
@EnableConfigurationProperties(HeaderSettingZuulFilterAutoConfig.Props.class)
public class HeaderSettingZuulFilterAutoConfig {

    @Bean
    @ConditionalOnMissingBean
    public HeaderSettingZuulFilter headerSettingFilter(Props props) {
        val filter = new HeaderSettingZuulFilter(props.getHeaders());
        filter.setOrder(props.getOrder());
        return filter;
    }

    @Getter
    @Setter
    @ConfigurationProperties(prefix = "carnival.api-gateway.header-setting")
    static class Props implements InitializingBean {

        private boolean enabled = true;
        private int order = 0;
        private Map<String, String> headers = new HashMap<>(1);

        @Override
        public void afterPropertiesSet() {
            if (enabled && headers.isEmpty()) {
                headers.put("Content-Type", "application/json;charset=UTF-8");
            }
        }
    }

}
