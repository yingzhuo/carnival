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

import com.github.yingzhuo.carnival.debug.ConditionalOnDebugMode;
import com.github.yingzhuo.carnival.debug.mvc.DebugMvcInterceptor;
import com.github.yingzhuo.carnival.debug.support.LogLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@ConditionalOnDebugMode
@ConditionalOnWebApplication
@EnableConfigurationProperties(DebugMvcConfiguration.Props.class)
public class DebugMvcConfiguration implements WebMvcConfigurer {

    private final Props props;

    public DebugMvcConfiguration(Props props, Environment env) {
        this.props = props;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new DebugMvcInterceptor(props.logLevel, props.loggerName)).addPathPatterns("/", "/**");
    }

    @Getter
    @Setter
    @ConfigurationProperties("carnival.debug")
    static class Props {
        private LogLevel logLevel = LogLevel.DEBUG;
        private String loggerName = DebugMvcConfiguration.class.getName();
    }

}
