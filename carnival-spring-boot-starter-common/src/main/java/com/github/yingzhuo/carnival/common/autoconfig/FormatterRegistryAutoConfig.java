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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.format.FormatterRegistry;

/**
 * @author 应卓
 */
public class FormatterRegistryAutoConfig {

    @Autowired(required = false)
    public void config(FormatterRegistry registry) {
        if (registry != null) {
            registry.addFormatterForFieldAnnotation(new DateTimeFormat.FormatterFactory());
            registry.addFormatterForFieldAnnotation(new IntCurrencyFormat.FormatterFactory());
            registry.addFormatterForFieldAnnotation(new LongCurrencyFormat.FormatterFactory());
        }
    }

    @Bean
    @ConfigurationPropertiesBinding
    public BooleanFormatter booleanFormatter() {
        return new BooleanFormatter();
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
