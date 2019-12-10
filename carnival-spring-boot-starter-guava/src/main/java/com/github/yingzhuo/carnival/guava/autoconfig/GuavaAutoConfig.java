/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.guava.autoconfig;

import com.github.yingzhuo.carnival.guava.CharSourceConverter;
import com.github.yingzhuo.carnival.guava.HostAndPortConverter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

/**
 * @author 应卓
 * @since 1.3.4
 */
public class GuavaAutoConfig {

    @Bean
    @Primary
    @ConditionalOnMissingBean
    @ConfigurationPropertiesBinding
    public HostAndPortConverter hostAndPortConverter() {
        return new HostAndPortConverter();
    }

    // --------------------------------------------------------------------------------------------------------------

    @Bean
    @Primary
    @ConditionalOnMissingBean
    @ConfigurationPropertiesBinding
    public CharSourceConverter charSourceConverter() {
        return new CharSourceConverter();
    }


}
