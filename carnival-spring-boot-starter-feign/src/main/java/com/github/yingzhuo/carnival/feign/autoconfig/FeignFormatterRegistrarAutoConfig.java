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

import lombok.Getter;
import lombok.Setter;
import lombok.val;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.FeignFormatterRegistrar;
import org.springframework.format.Formatter;
import org.springframework.format.FormatterRegistry;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * @author 应卓
 */
@EnableConfigurationProperties({
        FeignFormatterRegistrarAutoConfig.DateProps.class,
        FeignFormatterRegistrarAutoConfig.CalendarProps.class
})
public class FeignFormatterRegistrarAutoConfig implements FeignFormatterRegistrar {

    @Autowired
    private DateProps dateProps;

    @Autowired
    private CalendarProps calendarProps;

    @Override
    public void registerFormatters(FormatterRegistry registry) {
        registry.addFormatter(new DateFormatter(dateProps.getPattern()));
        registry.addFormatter(new CalendarFormatter(calendarProps.getPattern()));
    }

    private static class DateFormatter implements Formatter<Date> {
        private final String pattern;

        public DateFormatter(String pattern) {
            this.pattern = pattern;
        }

        @Override
        public Date parse(String text, Locale locale) throws ParseException {
            return DateUtils.parseDate(text, pattern);
        }

        @Override
        public String print(Date object, Locale locale) {
            return DateFormatUtils.format(object, pattern);
        }
    }

    private static class CalendarFormatter implements Formatter<Calendar> {
        private final String pattern;

        public CalendarFormatter(String pattern) {
            this.pattern = pattern;
        }

        @Override
        public Calendar parse(String text, Locale locale) throws ParseException {
            val date = DateUtils.parseDate(text, pattern);
            val cal = Calendar.getInstance();
            cal.setTime(date);
            return cal;
        }

        @Override
        public String print(Calendar object, Locale locale) {
            return DateFormatUtils.format(object, pattern);
        }
    }

    @Getter
    @Setter
    @ConfigurationProperties(prefix = "carnival.feign.date-formatter")
    public static class DateProps {
        private String pattern = "yyyy-MM-dd HH:mm:ss.SSS";
    }

    @Getter
    @Setter
    @ConfigurationProperties(prefix = "carnival.feign.calendar-formatter")
    public static class CalendarProps {
        private String pattern = "yyyy-MM-dd HH:mm:ss.SSS";
    }

}
