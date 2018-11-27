/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.datasource.autoconfig;

import com.github.yingzhuo.carnival.datasource.mvc.ForkDataSourceSwitchInterceptor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author 应卓
 */
@ConditionalOnWebApplication
@ConditionalOnProperty(prefix = "carnival.fork-datasource", name = "enabled", havingValue = "true", matchIfMissing = true)
@EnableConfigurationProperties(ForkDataSourceWebAutoConfig.Props.class)
public class ForkDataSourceWebAutoConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new ForkDataSourceSwitchInterceptor()).addPathPatterns("/", "/**").order(Ordered.HIGHEST_PRECEDENCE);
    }

    @Getter
    @Setter
    @ConfigurationProperties(prefix = "carnival.fork-datasource")
    public static class Props {
        private boolean enabled = true;
    }

}
