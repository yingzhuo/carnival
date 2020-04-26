/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.feign.converter;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.core.convert.converter.Converter;

import java.util.Calendar;

/**
 * @author 应卓
 * @since 1.6.0
 */
public class CalendarToStringConverter implements Converter<Calendar, String> {

    private final String pattern;

    public CalendarToStringConverter(String pattern) {
        this.pattern = pattern;
    }

    @Override
    public String convert(Calendar calendar) {
        return DateFormatUtils.format(calendar, pattern);
    }

}
