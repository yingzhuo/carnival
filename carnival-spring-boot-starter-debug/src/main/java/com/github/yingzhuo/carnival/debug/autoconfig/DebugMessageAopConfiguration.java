/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.debug.autoconfig;

import com.github.yingzhuo.carnival.debug.support.DebugMessageAdvice;
import com.github.yingzhuo.carnival.debug.support.LogLevel;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.stream.Collectors;

@Slf4j
@EnableConfigurationProperties(DebugMessageAopConfiguration.Props.class)
@ConditionalOnProperty(prefix = "carnival.debug.aop", name = "enabled", havingValue = "true", matchIfMissing = true)
public class DebugMessageAopConfiguration {

    private final Props props;

    public DebugMessageAopConfiguration(Props props) {
        this.props = props;
    }

    @PostConstruct
    private void init() {
        log.debug("SpringBoot auto-config: {}", getClass().getName());
    }

    @Bean
    @ConditionalOnMissingBean
    public DebugMessageAdvice debugMessageAdvice(Environment env) {
        boolean enabled = Arrays.stream(env.getActiveProfiles()).collect(Collectors.toSet()).contains(props.getProfile());
        return new DebugMessageAdvice(enabled, props.getLogLevel(), props.getLoggerName());
    }

    @Data
    @ConfigurationProperties("carnival.debug.aop")
    static class Props {
        private boolean enabled = true;
        private String profile = "debug";
        private LogLevel logLevel = LogLevel.DEBUG;
        private String loggerName = DebugMessageAdvice.class.getName();
    }

}
