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

import com.github.yingzhuo.carnival.password.encoder.PasswordEncoder;
import com.github.yingzhuo.carnival.password.encoder.SmartPasswordEncoder;
import com.github.yingzhuo.carnival.password.props.PasswordProps;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * @author 应卓
 */
@EnableConfigurationProperties(PasswordProps.class)
@ConditionalOnProperty(prefix = "carnival.password", name = "enabled", havingValue = "true", matchIfMissing = true)
public class PasswordEncoderAutoConfig {

    @Bean
    @ConditionalOnMissingBean
    public PasswordEncoder passwordEncoder(PasswordProps props) {
        return new SmartPasswordEncoder(
                props.getPasswordEncoder().getEncoding(),
                props.getPasswordEncoder().getUnmapped()
        );
    }

}
