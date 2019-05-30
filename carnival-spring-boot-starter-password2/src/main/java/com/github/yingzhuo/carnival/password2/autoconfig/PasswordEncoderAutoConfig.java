/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.password2.autoconfig;

import com.github.yingzhuo.carnival.password2.Algorithm;
import com.github.yingzhuo.carnival.password2.util.PasswordEncoderFactories;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author 应卓
 */
@SuppressWarnings("deprecation")
@ConditionalOnProperty(prefix = "carnival.password2", name = "enabled", havingValue = "true", matchIfMissing = true)
@EnableConfigurationProperties(PasswordEncoderAutoConfig.Props.class)
public class PasswordEncoderAutoConfig {

    @Bean
    @ConditionalOnMissingBean
    public PasswordEncoder passwordEncoder2(Props props) {
        if (props.isDelegatingMode()) {
            return PasswordEncoderFactories.createDelegatingPasswordEncoder(props.getAlgorithm());
        } else {
            return props.getAlgorithm().getPasswordEncoder();
        }
    }

    @Getter
    @Setter
    @ConfigurationProperties(prefix = "carnival.password2")
    static class Props {
        private boolean enabled = true;
        private Algorithm algorithm = Algorithm.MD5;
        private boolean delegatingMode = true;
    }

}
