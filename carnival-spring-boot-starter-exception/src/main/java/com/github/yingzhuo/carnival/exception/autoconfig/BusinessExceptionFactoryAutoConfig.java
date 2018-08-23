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

import com.github.yingzhuo.carnival.exception.BusinessExceptionFactory;
import com.github.yingzhuo.carnival.exception.impl.DefaultBusinessExceptionFactory;
import com.github.yingzhuo.carnival.exception.impl.IniBusinessExceptionFactory;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * @author 应卓
 */
@EnableConfigurationProperties(BusinessExceptionFactoryAutoConfig.Props.class)
public class BusinessExceptionFactoryAutoConfig {

    @Bean
    @ConditionalOnMissingBean
    public BusinessExceptionFactory businessExceptionFactory(Props props) {
        if (StringUtils.hasText(props.getIniLocation())) {
            return new IniBusinessExceptionFactory(props.getIniLocation());
        } else {
            return new DefaultBusinessExceptionFactory(props.getMessages());
        }
    }

    @Getter
    @Setter
    @ConfigurationProperties(prefix = "carnival.business-exception")
    static final class Props {
        private String iniLocation = null;
        private Map<String, String> messages = null;
    }

}
