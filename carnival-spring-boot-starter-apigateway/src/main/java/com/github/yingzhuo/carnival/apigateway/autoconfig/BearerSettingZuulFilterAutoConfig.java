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

import com.github.yingzhuo.carnival.apigateway.filter.BearerHttpZuulFilter;
import com.github.yingzhuo.carnival.common.io.ResourceText;
import lombok.Getter;
import lombok.Setter;
import lombok.val;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * @author 应卓
 * @since 1.1.4
 */
@ConditionalOnProperty(prefix = "carnival.api-gateway.bearer", name = "enabled", havingValue = "true")
@EnableConfigurationProperties(BearerSettingZuulFilterAutoConfig.Props.class)
public class BearerSettingZuulFilterAutoConfig {

    @Bean
    public BearerHttpZuulFilter bearerHttpHeaderSetterZuulFilter(Props props) {
        val filter = new BearerHttpZuulFilter(props.getValue());
        filter.setOrder(props.getOrder());
        return filter;
    }

    @Getter
    @Setter
    @ConfigurationProperties(prefix = "carnival.api-gateway.bearer")
    static final class Props implements InitializingBean {
        private boolean enabled = false;
        private String value = null;
        private String valueLocation = null;
        private int order = 0;

        @Override
        public void afterPropertiesSet() {
            if (enabled) {
                if (StringUtils.isNotBlank(valueLocation)) {
                    this.value = ResourceText.load(valueLocation);
                }
            }
        }
    }

}
