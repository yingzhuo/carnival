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

import lombok.var;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;

/**
 * @author 应卓
 * @since 1.10.28
 */
public final class Weeks {

    private Weeks() {
    }

    private static final String DEFAULT_DATE_PATTERN = "yyyy-MM-DD";
    private static final String DEFAULT_DELIMITER = "_";

    public static String add(String week, int n) {
        return add(DEFAULT_DATE_PATTERN, DEFAULT_DELIMITER, week, n);
    }

    public static String add(String datePattern, String delimiter, String week, int n) {
        try {
            String[] parts = week.split(delimiter, 2);
            var d1 = DateUtils.parseDate(parts[0], datePattern);
            var d2 = DateUtils.parseDate(parts[1], datePattern);
            d1 = DateUtils.addDays(d1, 7 * n);
            d2 = DateUtils.addDays(d1, 7 * n);
            return String.format(
                    "%s%s%s",
                    DateFormatUtils.format(d1, datePattern),
                    delimiter,
                    DateFormatUtils.format(d2, datePattern)
            );
        } catch (ParseException e) {
            throw new IllegalArgumentException(e.getMessage(), e);
        }
    }

    public static String sub(String week, int n) {
        return sub(DEFAULT_DATE_PATTERN, DEFAULT_DELIMITER, week, n);
    }

    public static String sub(String datePattern, String delimiter, String week, int n) {
        return add(datePattern, delimiter, week, -n);
    }

}
