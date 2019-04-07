/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.tool.autoconfig;

import com.github.yingzhuo.carnival.tool.KeyMapper;
import com.github.yingzhuo.carnival.tool.impl.DefaultKeyMapper;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * @author 应卓
 */
@Slf4j
@EnableConfigurationProperties(KeyMapperAutoConfig.Props.class)
@ConditionalOnProperty(prefix = "carnival.tool.key-mapper", name = "enabled", havingValue = "true", matchIfMissing = false)
public class KeyMapperAutoConfig {

    @Bean
    @ConditionalOnMissingBean
    public KeyMapper keyMapper(Props props) {
        return new DefaultKeyMapper(props.getPrefix(), props.getSuffix());
    }

    @Getter
    @Setter
    @ConfigurationProperties(prefix = "carnival.tool.key-mapper")
    static class Props {
        private boolean enabled = false;
        private String prefix = "";
        private String suffix = "";
    }

}
