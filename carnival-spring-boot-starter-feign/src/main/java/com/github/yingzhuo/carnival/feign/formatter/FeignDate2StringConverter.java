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

import java.util.Date;
import java.util.function.Function;

/**
 * @author 应卓
 */
public class FeignDate2StringConverter implements Converter<Date, String>, Function<Date, String> {

    private final String pattern;

    public FeignDate2StringConverter(String pattern) {
        this.pattern = pattern;
    }

    @Override
    public String convert(Date date) {
        return DateFormatUtils.format(date, pattern);
    }

    @Override
    public final String apply(Date date) {
        return convert(date);
    }

}
