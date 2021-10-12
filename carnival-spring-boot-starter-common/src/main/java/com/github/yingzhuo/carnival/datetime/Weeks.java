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

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * @author 应卓
 * @since 1.10.28
 */
public final class Weeks {

    private static final DateTimeFormatter YEAR_WEEK = DateTimeFormatter.ofPattern("YYYY-w-e", Locale.getDefault());
    private static final DateTimeFormatter DATE = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.getDefault());

    private Weeks() {
    }

    public static String create(int year, int week) {
        final LocalDate date = LocalDate.parse(year + "-" + week + "-1", YEAR_WEEK);
        return String.format("%s<=>%s",
                DATE.format(date),
                DATE.format(date.plusDays(6))
        );
    }

    public static String add(String week, int n) {
        LocalDate date = LocalDate.parse(week.split("<=>", 2)[0], DATE);
        return String.format("%s<=>%s",
                DATE.format(date.plusDays(7 * n)),
                DATE.format(date.plusDays(7 * n + 6))
        );
    }

    public static String sub(String week, int n) {
        LocalDate date = LocalDate.parse(week.split("<=>", 2)[0], DATE);
        return String.format("%s<=>%s",
                DATE.format(date.minusDays(7 * n)),
                DATE.format(date.minusDays(7 * n + 6))
        );
    }

}
