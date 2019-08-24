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

import com.github.yingzhuo.carnival.apigateway.filter.LoggingFilter;
import com.github.yingzhuo.carnival.common.condition.ConditionalOnDebugMode;
import lombok.Getter;
import lombok.Setter;
import lombok.val;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;

/**
 * @author 应卓
 */
@ConditionalOnProperty(prefix = "carnival.api-gateway.logging", name = "enabled", havingValue = "true", matchIfMissing = true)
@ConditionalOnDebugMode
@EnableConfigurationProperties(LoggingFilterAutoConfig.Props.class)
public class LoggingFilterAutoConfig {

    @Bean
    @ConditionalOnMissingBean
    public LoggingFilter loggingFilter(Props props) {
        val filter = new LoggingFilter();
        filter.setOrder(props.getOrder());
        return filter;
    }

    @Getter
    @Setter
    @ConfigurationProperties(prefix = "carnival.api-gateway.logging")
    static class Props {
        private boolean enabled = true;
        private int order = Ordered.LOWEST_PRECEDENCE;
    }

}
