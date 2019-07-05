/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.common.util;

import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

/**
 * @author 应卓
 */
public final class IdentityCardNumberUtils {

    private IdentityCardNumberUtils() {
    }

    public static boolean isMale(String number) {
        int n = Integer.parseInt(number.substring(16, 17));
        return n % 2 == 1;
    }

    public static boolean isFemale(String number) {
        int n = Integer.parseInt(number.substring(16, 17));
        return n % 2 == 0;
    }

    public static int getYearOfBirth(String number) {
        try {
            String dob = number.substring(6, 14);
            System.out.println(dob);
            Date date = DateUtils.parseDate(dob, "yyyyMMdd");
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            return cal.get(Calendar.YEAR);
        } catch (ParseException e) {
            throw new IllegalArgumentException(e.getMessage(), e);
        }
    }

    public static int getAge(String number) {
        String dob = number.substring(6, 14);
        return Period.between(LocalDate.parse(dob, DateTimeFormatter.ofPattern("yyyyMMdd")), LocalDate.now()).getYears();
    }

}
