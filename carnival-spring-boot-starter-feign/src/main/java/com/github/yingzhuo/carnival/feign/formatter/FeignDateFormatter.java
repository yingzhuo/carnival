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
import java.util.Date;
import java.util.Locale;

/**
 * @author 应卓
 */
public class FeignDateFormatter implements Formatter<Date> {

    private final String pattern;

    public FeignDateFormatter(String pattern) {
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
