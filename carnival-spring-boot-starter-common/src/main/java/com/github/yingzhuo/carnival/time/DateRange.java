/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.time;

import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.time.Duration;
import java.util.Date;
import java.util.Iterator;

/**
 * @author 应卓
 * @since 1.6.25
 */
public final class DateRange {

    private static final String[] PATTERNS = new String[]{
            "yyyy-MM-dd",
            "yyyy-MM-dd HH:mm:ss",
            "yyyy-MM-dd HH:mm:ss.SSS",
            "yyyy-MM-dd'T'HH:mm:ss",
            "yyyy-MM-dd'T'HH:mm:ss.SSS",
            "yyyy-MM-dd'T'HH:mm:ss.SSSZ",
            "yyyy-MM-dd'T'HH:mm:ss.SSS Z",
            "yyyyMMdd",
            "yyMMddHHmmss",
            "yyMMddHHmmssSSS"
    };

    private DateRange() {
    }

    public static Iterator<Date> iterator(String beginInclude, String endExclude) {
        return iterator(beginInclude, endExclude, Duration.ofDays(1));
    }

    public static Iterator<Date> iterator(String beginInclude, String endExclude, Duration step) {
        try {
            return iterator(
                    DateUtils.parseDate(beginInclude, PATTERNS),
                    DateUtils.parseDate(endExclude, PATTERNS),
                    step
            );
        } catch (ParseException e) {
            throw new IllegalArgumentException(e.getMessage(), e);
        }
    }

    public static Iterator<Date> iterator(Date beginInclude, Date endExclude) {
        return new DateRangeIterator(beginInclude, endExclude, Duration.ofDays(1));
    }

    public static Iterator<Date> iterator(Date beginInclude, Date end, Duration step) {
        return new DateRangeIterator(beginInclude, end, step);
    }

}
