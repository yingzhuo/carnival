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
 * @since 1.5.1
 */
public class ConverterRegistryAutoConfig {

    @Bean
    @ConfigurationPropertiesBinding
    public StringToBooleanConverter stringToBooleanConverter2() {
        return new StringToBooleanConverter();
    }

    @Bean
    @ConfigurationPropertiesBinding
    public StringToDateConverter stringToDateConverter() {
        return new StringToDateConverter();
    }

    @Bean
    @ConfigurationPropertiesBinding
    public StringToCalendarConverter stringToCalendarConverter() {
        return new StringToCalendarConverter();
    }

    @Bean
    @ConfigurationPropertiesBinding
    public StringToGenderConverter stringToGenderConverter() {
        return new StringToGenderConverter();
    }

    @Bean
    @ConfigurationPropertiesBinding
    public StringToIntConverter stringToIntConverter() {
        return new StringToIntConverter();
    }

    @Bean
    @ConfigurationPropertiesBinding
    public StringToLongConverter stringToLongConverter() {
        return new StringToLongConverter();
    }

    @Bean
    @ConfigurationPropertiesBinding
    public StringToDoubleConverter stringToDoubleConverter() {
        return new StringToDoubleConverter();
    }

    @Bean
    @ConfigurationPropertiesBinding
    public StringToHostPortConverter stringToHostPortConverter() {
        return new StringToHostPortConverter();
    }

}
