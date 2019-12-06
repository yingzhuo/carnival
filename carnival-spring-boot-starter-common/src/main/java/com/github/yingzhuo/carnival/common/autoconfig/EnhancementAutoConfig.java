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

import com.github.yingzhuo.carnival.common.datamodel.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.context.annotation.Bean;

/**
 * @author 应卓
 */
public class EnhancementAutoConfig {

    @Bean
    @ConditionalOnMissingBean
    @ConfigurationPropertiesBinding
    public BooleanFormatter booleanFormatter() {
        return new BooleanFormatter();
    }

    @Bean
    @ConditionalOnMissingBean
    @ConfigurationPropertiesBinding
    public IntFormatter intFormatter() {
        return new IntFormatter();
    }

    @Bean
    @ConditionalOnMissingBean
    @ConfigurationPropertiesBinding
    public LongFormatter longFormatter() {
        return new LongFormatter();
    }

    @Bean
    @ConditionalOnMissingBean
    @ConfigurationPropertiesBinding
    public DoubleFormatter doubleFormatter() {
        return new DoubleFormatter();
    }

    @Bean
    @ConditionalOnMissingBean
    @ConfigurationPropertiesBinding
    public DateFormatter dateFormatter() {
        return new DateFormatter();
    }

    @Bean
    @ConditionalOnMissingBean
    @ConfigurationPropertiesBinding
    public CalendarFormatter calendarFormatter() {
        return new CalendarFormatter();
    }

    @Bean
    @ConditionalOnMissingBean
    @ConfigurationPropertiesBinding
    public GenderFormatter genderFormatter() {
        return new GenderFormatter();
    }

    @Bean
    @ConditionalOnMissingBean
    @ConfigurationPropertiesBinding
    public ResourceOptionalFormatter resourceOptionalFormatter() {
        return new ResourceOptionalFormatter(",");
    }

    @Bean
    @ConditionalOnMissingBean
    @ConfigurationPropertiesBinding
    public HostAndPortFormatter hostAndPortFormatter() {
        return new HostAndPortFormatter();
    }

}
