/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.datetime;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.core.convert.converter.Converter;

import java.text.ParseException;

/**
 * @author 应卓
 * @since 1.9.10
 */
@SuppressWarnings("NullableProblems")
public class DatePairConverter implements Converter<String, DatePair> {

    private static final String DEFAULT_PATTERN = "yyyy-MM-dd";
    private static final String DEFAULT_SPLITTER = "@@";

    private final String pattern;
    private final String splitter;

    public DatePairConverter() {
        this(DEFAULT_PATTERN, DEFAULT_SPLITTER);
    }

    public DatePairConverter(String pattern, String splitter) {
        this.pattern = pattern;
        this.splitter = splitter;
    }

    @Override
    public DatePair convert(String source) {
        if (source == null) {
            return null;
        }

        final String[] array = source.split(this.splitter, 2);

        try {
            return new DatePair(
                    DateUtils.parseDate(array[0], this.pattern),
                    DateUtils.parseDate(array[1], this.pattern)
            );
        } catch (ParseException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

}
