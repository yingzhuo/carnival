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
import com.github.yingzhuo.carnival.event.ApplicationInitializerHolder;
import com.github.yingzhuo.carnival.spring.ApplicationContextProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

/**
 * @author 应卓
 * @since 1.5.2
 */
@Lazy(false)
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

    @Bean
    @ConfigurationPropertiesBinding
    @ConditionalOnMissingBean
    public StringToColorConverter stringToColorConverter() {
        return new StringToColorConverter();
    }

    // -----------------------------------------------------------------------------------------------------------------

    @Bean
    @ConditionalOnMissingBean
    public PathMatcher pathMatcher() {
        return new AntPathMatcher();
    }

    // -----------------------------------------------------------------------------------------------------------------

    @Bean
    public ApplicationInitializerHolder applicationInitializerHolder() {
        return new ApplicationInitializerHolder();
    }

    // -----------------------------------------------------------------------------------------------------------------

    @Bean
    @ConditionalOnMissingBean
    public ApplicationContextProvider applicationContextProvider() {
        return ApplicationContextProvider.INSTANCE;
    }

}
