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

import com.github.yingzhuo.carnival.common.converter.CurrencyFormat;
import com.github.yingzhuo.carnival.common.converter.DateTimeConverter;
import com.github.yingzhuo.carnival.common.converter.ResourceConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.format.FormatterRegistry;

/**
 * @author 应卓
 * @since 1.6.6
 */
public class FormatterAutoConfig {

    @Autowired(required = false)
    public void config(FormatterRegistry registry) {
        if (registry != null) {
            registry.addFormatterForFieldAnnotation(new CurrencyFormat.FormatterFactory());
        }
    }

    @Bean
    @ConditionalOnMissingBean
    @ConfigurationPropertiesBinding
    public ResourceConverter resourceConverter() {
        return new ResourceConverter();
    }

    @Bean
    @ConditionalOnMissingBean
    @ConfigurationPropertiesBinding
    public DateTimeConverter dateTimeConverter() {
        return new DateTimeConverter();
    }

}
