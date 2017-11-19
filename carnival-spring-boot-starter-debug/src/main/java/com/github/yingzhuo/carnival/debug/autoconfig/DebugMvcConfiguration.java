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

import com.github.yingzhuo.carnival.debug.mvc.DebugMvcInterceptor;
import com.github.yingzhuo.carnival.debug.support.LogLevel;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.stream.Collectors;

@ConditionalOnWebApplication
@EnableConfigurationProperties(DebugMvcConfiguration.Props.class)
@ConditionalOnProperty(prefix = "carnival.debug", name = "enabled", havingValue = "true", matchIfMissing = true)
@Slf4j
public class DebugMvcConfiguration extends WebMvcConfigurerAdapter {

    private final Props props;
    private final Environment env;

    public DebugMvcConfiguration(Props props, Environment env) {
        this.props = props;
        this.env = env;
    }

    @PostConstruct
    private void init() {
        log.debug("SpringBoot auto-config: {}", getClass().getName());
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(
                new DebugMvcInterceptor(
                        Arrays.stream(env.getActiveProfiles()).collect(Collectors.toSet()).contains(props.getProfile()),
                        props.getLogLevel(),
                        props.getLoggerName()))
                .addPathPatterns("/", "/**");
    }

    @Data
    @ConfigurationProperties("carnival.debug")
    static class Props {
        private boolean enabled = true;
        private String profile = "debug";
        private LogLevel logLevel = LogLevel.DEBUG;
        private String loggerName = DebugMvcConfiguration.class.getName();
    }

}
