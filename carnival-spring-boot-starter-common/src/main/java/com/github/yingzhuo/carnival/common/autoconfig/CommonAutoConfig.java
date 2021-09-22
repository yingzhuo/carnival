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

import com.github.yingzhuo.carnival.common.converter.ByteArrayConverter;
import com.github.yingzhuo.carnival.common.converter.ColorConverter;
import com.github.yingzhuo.carnival.common.converter.DateTimeConverter;
import com.github.yingzhuo.carnival.common.converter.DateTimeNewConverter;
import com.github.yingzhuo.carnival.common.io.ResourceOptionConverter;
import com.github.yingzhuo.carnival.common.log.ConfigurableLoggerConverter;
import com.github.yingzhuo.carnival.datetime.DatePairConverter;
import com.github.yingzhuo.carnival.spring.ApplicationContextProvider;
import com.github.yingzhuo.carnival.spring.BeanFinder;
import com.github.yingzhuo.carnival.spring.BeanFinderAwareBeanPostProcessor;
import com.github.yingzhuo.carnival.spring.springid.DefaultSpringIdProvider;
import com.github.yingzhuo.carnival.spring.springid.SpringIdProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

/**
 * @author 应卓
 * @since 1.5.2
 */
class CommonAutoConfig {

    @Bean
    @ConditionalOnMissingBean
    @ConfigurationPropertiesBinding
    ByteArrayConverter byteArrayConverter() {
        return new ByteArrayConverter();
    }

    @Bean
    @ConditionalOnMissingBean
    @ConfigurationPropertiesBinding
    ResourceOptionConverter resourceOptionConverter() {
        return new ResourceOptionConverter();
    }

    @Bean
    @ConditionalOnMissingBean
    @ConfigurationPropertiesBinding
    DateTimeConverter dateTimeConverter() {
        return new DateTimeConverter();
    }

    @Bean
    @ConditionalOnMissingBean
    @ConfigurationPropertiesBinding
    DateTimeNewConverter dateTimeNewConverter() {
        return new DateTimeNewConverter();
    }

    @Bean
    @ConditionalOnMissingBean
    @ConfigurationPropertiesBinding
    ColorConverter colorConverter() {
        return new ColorConverter();
    }

    // ----------------------------------------------------------------------------------------------------------------

    @Bean
    @ConditionalOnMissingBean
    @ConfigurationPropertiesBinding
    DatePairConverter datePairConverter() {
        return new DatePairConverter();
    }

    // ----------------------------------------------------------------------------------------------------------------

    @Bean
    @ConditionalOnMissingBean
    @ConfigurationPropertiesBinding
    ConfigurableLoggerConverter configurableLoggerConverter() {
        return new ConfigurableLoggerConverter();
    }

    // ----------------------------------------------------------------------------------------------------------------

    @Bean
    @ConditionalOnMissingBean
    PathMatcher pathMatcher() {
        return new AntPathMatcher();
    }

    @Bean
    @ConditionalOnMissingBean
    ApplicationContextProvider applicationContextProvider() {
        return ApplicationContextProvider.INSTANCE;
    }

    @Bean
    @ConditionalOnMissingBean
    SpringIdProvider springIdProvider(Environment environment) {
        return new DefaultSpringIdProvider(environment);
    }

    // ----------------------------------------------------------------------------------------------------------------

    @Bean
    @ConditionalOnMissingBean
    BeanFinder beanFinder(ApplicationContext context) {
        return new BeanFinder(context);
    }

    @Bean
    @ConditionalOnMissingBean
    BeanFinderAwareBeanPostProcessor beanFinderAwareBeanPostProcessor(BeanFinder beanFinder) {
        return new BeanFinderAwareBeanPostProcessor(beanFinder);
    }

}
