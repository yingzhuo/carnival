/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.exception.autoconfig;

import com.github.yingzhuo.carnival.common.io.ResourceOption;
import com.github.yingzhuo.carnival.exception.business.BusinessExceptionFactory;
import com.github.yingzhuo.carnival.exception.business.impl.InMemoryBusinessExceptionFactory;
import com.github.yingzhuo.carnival.exception.business.impl.TomlBusinessExceptionFactory;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;

import java.util.Map;

/**
 * @author 应卓
 */
@EnableConfigurationProperties(BusinessExceptionFactoryAutoConfig.Props.class)
@ConditionalOnProperty(prefix = "carnival.business-exception", name = "enabled", havingValue = "true", matchIfMissing = true)
public class BusinessExceptionFactoryAutoConfig {

    @Bean
    @ConditionalOnMissingBean
    public BusinessExceptionFactory businessExceptionFactory(Props props) {

        if (props.getTomlLocation() != null && props.getTomlLocation().exists()) {
            return new TomlBusinessExceptionFactory(props.getTomlLocation());
        }

        ResourceOption option = ResourceOption.of(
                "classpath:/business-exception.toml",
                "classpath:/business-exceptions.toml",
                "classpath:/META-INF/business-exception.toml",
                "classpath:/META-INF/business-exceptions.toml",
                "file:./business-exception.toml",
                "file:./business-exceptions.toml"
        );

        if (option.isPresent()) {
            return new TomlBusinessExceptionFactory(option.get());
        }

        return new InMemoryBusinessExceptionFactory(props.getMessages());
    }

    @Getter
    @Setter
    @ConfigurationProperties(prefix = "carnival.business-exception")
    static final class Props {
        private boolean enabled = true;
        private Resource tomlLocation = null;
        private Map<String, String> messages;
    }

}
