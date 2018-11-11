/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.feign.formatter;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.core.convert.converter.Converter;

import java.util.Calendar;
import java.util.function.Function;

/**
 * @author 应卓
 */
public class FeignCalendar2StringConverter implements Converter<Calendar, String>, Function<Calendar, String> {

    private final String pattern;

    public FeignCalendar2StringConverter(String pattern) {
        this.pattern = pattern;
    }

    @Override
    public String convert(Calendar calendar) {
        return DateFormatUtils.format(calendar, pattern);
    }

    @Override
    public final String apply(Calendar calendar) {
        return convert(calendar);
    }

}
