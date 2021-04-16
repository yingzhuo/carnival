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
import com.github.yingzhuo.carnival.spring.ApplicationContextProvider;
import com.github.yingzhuo.carnival.spring.springid.DefaultSpringIdProvider;
import com.github.yingzhuo.carnival.spring.springid.SpringIdProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

/**
 * @author 应卓
 * @since 1.5.2
 */
public class CommonAutoConfig {

    @Bean
    @ConfigurationPropertiesBinding
    @ConditionalOnMissingBean
    public StringToBooleanConverter stringToBooleanConverter() {
        return new StringToBooleanConverter();
    }

    @Bean
    @ConfigurationPropertiesBinding
    @ConditionalOnMissingBean
    public StringToCalendarConverter stringToCalendarConverter() {
        return new StringToCalendarConverter();
    }

    @Bean
    @ConfigurationPropertiesBinding
    @ConditionalOnMissingBean
    public StringToDateConverter stringToDateConverter() {
        return new StringToDateConverter();
    }

    @Bean
    @ConfigurationPropertiesBinding
    @ConditionalOnMissingBean
    public StringToDoubleConverter stringToDoubleConverter() {
        return new StringToDoubleConverter();
    }

    @Bean
    @ConfigurationPropertiesBinding
    @ConditionalOnMissingBean
    public StringToIntConverter stringToIntConverter() {
        return new StringToIntConverter();
    }

    @Bean
    @ConfigurationPropertiesBinding
    @ConditionalOnMissingBean
    public StringToLongConverter stringToLongConverter() {
        return new StringToLongConverter();
    }

    @Bean
    @ConfigurationPropertiesBinding
    @ConditionalOnMissingBean
    public StringToGenderConverter stringToGenderConverter() {
        return new StringToGenderConverter();
    }

    @Bean
    @ConfigurationPropertiesBinding
    @ConditionalOnMissingBean
    public StringToHostAndPortConverter stringToHostAndPortConverter() {
        return new StringToHostAndPortConverter();
    }

    @Bean
    @ConfigurationPropertiesBinding
    @ConditionalOnMissingBean
    public StringToResourceTextConverter stringToResourceTextConverter() {
        return new StringToResourceTextConverter();
    }

    @Bean
    @ConfigurationPropertiesBinding
    @ConditionalOnMissingBean
    public StringToResourceOptionalConverter stringToResourceOptionalConverter() {
        return new StringToResourceOptionalConverter();
    }

    // -----------------------------------------------------------------------------------------------------------------

    @Bean
    @ConditionalOnMissingBean
    public PathMatcher pathMatcher() {
        return new AntPathMatcher();
    }

    // -----------------------------------------------------------------------------------------------------------------

    @Bean
    @ConditionalOnMissingBean
    public ApplicationContextProvider applicationContextProvider() {
        return ApplicationContextProvider.INSTANCE;
    }

    // -----------------------------------------------------------------------------------------------------------------

    @Bean
    @ConditionalOnMissingBean
    public SpringIdProvider springIdProvider(Environment environment) {
        return new DefaultSpringIdProvider(environment);
    }

}
