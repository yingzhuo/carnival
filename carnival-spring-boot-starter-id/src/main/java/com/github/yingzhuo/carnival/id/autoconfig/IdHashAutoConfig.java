/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.id.autoconfig;

import com.github.yingzhuo.carnival.id.IdHash;
import com.github.yingzhuo.carnival.id.hash.IdHashImpl;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * @author 应卓
 * @since 1.8.7
 */
@EnableConfigurationProperties(IdHashAutoConfig.Properties.class)
@ConditionalOnProperty(prefix = "carnival.id-hash", name = "enabled", havingValue = "true", matchIfMissing = true)
public class IdHashAutoConfig {

    @Bean
    @ConditionalOnMissingBean
    public IdHash idHash(Properties props) {
        return new IdHashImpl(
                props.getSalt(),
                props.getMinLength(),
                props.getChars()
        );
    }

    @Getter
    @Setter
    @ConfigurationProperties(prefix = "carnival.id-hash")
    static class Properties {
        private boolean enabled = true;
        private String salt = Properties.class.getName();
        private int minLength = 6;
        private String chars = "";
    }

}
