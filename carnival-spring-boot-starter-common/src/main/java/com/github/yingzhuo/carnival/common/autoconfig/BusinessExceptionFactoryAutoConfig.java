/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.common.autoconfig;

import com.github.yingzhuo.carnival.exception.business.BusinessExceptionFactory;
import com.github.yingzhuo.carnival.exception.business.impl.InMemoryBusinessExceptionFactory;
import com.github.yingzhuo.carnival.exception.business.impl.PropertySourceBusinessExceptionFactory;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;

import java.util.Map;

/**
 * @author 应卓
 */
@Lazy(false)
@EnableConfigurationProperties({
        BusinessExceptionFactoryAutoConfig.Props.class,
        PropertySourceBusinessExceptionFactory.Env.class}
)
@ConditionalOnProperty(prefix = "carnival.business-exception", name = "enabled", havingValue = "true", matchIfMissing = true)
public class BusinessExceptionFactoryAutoConfig {

    @Bean
    @ConditionalOnMissingBean
    public BusinessExceptionFactory businessExceptionFactory(Props props, PropertySourceBusinessExceptionFactory.Env env) {

        if (env != null && !env.isEmpty()) {
            return new PropertySourceBusinessExceptionFactory(env);
        }

        return new InMemoryBusinessExceptionFactory(props.getMessages());
    }

    @Getter
    @Setter
    @ConfigurationProperties(prefix = "carnival.business-exception")
    static final class Props {
        private boolean enabled = true;
        private Map<String, String> messages;
    }

}
