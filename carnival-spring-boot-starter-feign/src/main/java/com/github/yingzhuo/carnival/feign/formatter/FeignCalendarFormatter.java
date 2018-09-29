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
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.format.Formatter;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Locale;

/**
 * @author 应卓
 */
public class FeignCalendarFormatter implements Formatter<Calendar> {

    private final String pattern;

    public FeignCalendarFormatter(String pattern) {
        this.pattern = pattern;
    }

    @Override
    public Calendar parse(String text, Locale locale) throws ParseException {
        return DateUtils.toCalendar(DateUtils.parseDate(text, pattern));
    }

    @Override
    public String print(Calendar object, Locale locale) {
        return DateFormatUtils.format(object, pattern);
    }

}
