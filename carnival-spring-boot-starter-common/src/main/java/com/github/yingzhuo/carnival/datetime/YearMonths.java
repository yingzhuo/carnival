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

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

/**
 * @author 应卓
 * @since 1.10.28
 */
public final class YearMonths {

    private static final String DEFAULT_PATTERN = "yyyy-MM";

    private YearMonths() {
    }

    public static String add(String yearMonth, int n) {
        return add(DEFAULT_PATTERN, yearMonth, n);
    }

    public static String add(String pattern, String yearMonth, int n) {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        final YearMonth ym = YearMonth.parse(yearMonth, formatter);
        return ym.plusMonths(n).format(formatter);
    }

    public static String sub(String yearMonth, int n) {
        return sub(DEFAULT_PATTERN, yearMonth, n);
    }

    public static String sub(String pattern, String yearMonth, int n) {
        return add(pattern, yearMonth, -n);
    }

}
