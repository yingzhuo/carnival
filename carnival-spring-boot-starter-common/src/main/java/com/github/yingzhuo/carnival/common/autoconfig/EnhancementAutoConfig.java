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
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.context.annotation.Bean;

/**
 * @author 应卓
 */
public class EnhancementAutoConfig {

    @Bean
    @ConfigurationPropertiesBinding
    public BooleanFormatter booleanFormatter() {
        return new BooleanFormatter();
    }

    @Bean
    @ConfigurationPropertiesBinding
    public IntFormatter intFormatter() {
        return new IntFormatter();
    }

    @Bean
    @ConfigurationPropertiesBinding
    public LongFormatter longFormatter() {
        return new LongFormatter();
    }

    @Bean
    @ConfigurationPropertiesBinding
    public DoubleFormatter doubleFormatter() {
        return new DoubleFormatter();
    }

    @Bean
    @ConfigurationPropertiesBinding
    public DateFormatter dateFormatter() {
        return new DateFormatter();
    }

    @Bean
    @ConfigurationPropertiesBinding
    public CalendarFormatter calendarFormatter() {
        return new CalendarFormatter();
    }

    @Bean
    @ConfigurationPropertiesBinding
    public GenderFormatter genderFormatter() {
        return new GenderFormatter();
    }

    @Bean
    @ConfigurationPropertiesBinding
    public HostAndPortFormatter hostAndPortFormatter() {
        return new HostAndPortFormatter();
    }

}
