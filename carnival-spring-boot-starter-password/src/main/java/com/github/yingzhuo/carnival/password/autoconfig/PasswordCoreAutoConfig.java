/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.password.autoconfig;

import com.github.yingzhuo.carnival.password.PasswordEncoder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * @author 应卓
 */
@EnableConfigurationProperties(PasswordCoreAutoConfig.PasswordEncoderProperties.class)
@ConditionalOnProperty(prefix = "carnival.password", name = "enabled", havingValue = "true", matchIfMissing = true)
public class PasswordCoreAutoConfig {

    @Bean
    @ConditionalOnMissingBean
    public PasswordEncoder passwordEncoder(PasswordEncoderProperties props) {
        return new PasswordEncoderImpl(props.getEncoding(), props.getUnmapped());
    }

    @Getter
    @Setter
    @ConfigurationProperties(prefix = "carnival.password")
    static class PasswordEncoderProperties {
        private boolean enabled = true;
        private Algorithm encoding = Algorithm.bcrypt;
        private Algorithm unmapped = Algorithm.bcrypt;
    }

}
