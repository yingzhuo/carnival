/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.json.autoconfig;

import com.fasterxml.jackson.datatype.jsr310.ser.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;

import java.time.format.DateTimeFormatter;

/**
 * @author 应卓
 * @since 1.4.3
 */
@Lazy(false)
@EnableConfigurationProperties(JacksonCoreAutoConfig.Props.class)
@ConditionalOnProperty(prefix = "carnival.jackson", name = "enabled", havingValue = "true", matchIfMissing = true)
public class JacksonCoreAutoConfig {

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jsonCustomizer(Props props) {
        return builder -> {
            builder.serializers(new LocalDateSerializer(DateTimeFormatter.ofPattern(props.getLocalDatePattern())));
            builder.serializers(new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(props.getLocalTimePattern())));
            builder.serializers(new LocalTimeSerializer(DateTimeFormatter.ofPattern(props.getLocalTimePattern())));
            builder.serializers(new ZonedDateTimeSerializer(DateTimeFormatter.ofPattern(props.getZonedDateTimePattern())));
            builder.serializers(new YearMonthSerializer(DateTimeFormatter.ofPattern(props.getYearMonthPattern())));
            builder.serializers(new MonthDaySerializer(DateTimeFormatter.ofPattern(props.getMonthDayPattern())));
        };
    }

    @Getter
    @Setter
    @ConfigurationProperties(prefix = "carnival.jackson")
    static class Props {
        private boolean enabled = true;
        private String localDatePattern = "yyyy-MM-dd";
        private String localDateTimePattern = "yyyy-MM-dd'T'HH:mm:ss";
        private String localTimePattern = "HH:mm:ss";
        private String zonedDateTimePattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
        private String yearMonthPattern = "yyyy-MM";
        private String monthDayPattern = "MM-dd";
    }

}
