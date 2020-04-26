/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.feign.autoconfig;

import com.github.yingzhuo.carnival.feign.converter.CalendarToStringConverter;
import com.github.yingzhuo.carnival.feign.converter.DateToStringConverter;
import com.github.yingzhuo.carnival.feign.props.FeignCoreProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.FeignFormatterRegistrar;
import org.springframework.format.FormatterRegistry;

/**
 * @author 应卓
 * @since 1.6.0
 */
@EnableConfigurationProperties(FeignCoreProperties.class)
@ConditionalOnProperty(prefix = "carnival.feign", name = "enabled", havingValue = "true", matchIfMissing = true)
public class FeignBeanAutoConfig implements FeignFormatterRegistrar {

    @Autowired
    private FeignCoreProperties properties;

    @Override
    public void registerFormatters(FormatterRegistry registry) {
        registry.addConverter(new DateToStringConverter(properties.getDefaultDatePattern()));
        registry.addConverter(new CalendarToStringConverter(properties.getDefaultCalendarPattern()));
    }

}
