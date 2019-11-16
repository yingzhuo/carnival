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

import com.github.yingzhuo.carnival.exception.business.BusinessExceptionFactory;
import com.github.yingzhuo.carnival.exception.business.impl.InMemoryBusinessExceptionFactory;
import com.github.yingzhuo.carnival.exception.business.impl.IniBusinessExceptionFactory;
import com.github.yingzhuo.carnival.exception.business.impl.TomlBusinessExceptionFactory;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@EnableConfigurationProperties(BusinessExceptionFactoryAutoConfig.Props.class)
@ConditionalOnProperty(prefix = "carnival.business-exception", name = "enabled", havingValue = "true", matchIfMissing = true)
public class BusinessExceptionFactoryAutoConfig {

    @Bean
    @ConditionalOnMissingBean
    public BusinessExceptionFactory businessExceptionFactory(Props props) {

        if (props.getTomlLocation() != null && props.getTomlLocation().isReadable()) {
            return new TomlBusinessExceptionFactory(props.getTomlLocation());
        }

        if (props.getIniLocation() != null && props.getIniLocation().isReadable()) {
            return new IniBusinessExceptionFactory(props.getIniLocation());
        }

        return new InMemoryBusinessExceptionFactory(props.getMessages());
    }

    @Getter
    @Setter
    @ConfigurationProperties(prefix = "carnival.business-exception")
    static final class Props {
        private boolean enabled = true;

        private Resource tomlLocation = null;

        @Deprecated
        private Resource iniLocation = null;

        private Map<String, String> messages = null;
    }

}
