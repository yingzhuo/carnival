/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.localization.china.util;

import com.github.yingzhuo.carnival.localization.china.tool.IdentityDescriptor;
import com.github.yingzhuo.carnival.localization.china.tool.IdentityParser;
import com.github.yingzhuo.carnival.spring.SpringUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 应卓
 */
public final class IdentityUtils {

    private final static Map<Integer, String> ZONE_NUM = new HashMap<>();

    static {
        ZONE_NUM.put(11, "北京");
        ZONE_NUM.put(12, "天津");
        ZONE_NUM.put(13, "河北");
        ZONE_NUM.put(14, "山西");
        ZONE_NUM.put(15, "内蒙古");
        ZONE_NUM.put(21, "辽宁");
        ZONE_NUM.put(22, "吉林");
        ZONE_NUM.put(23, "黑龙江");
        ZONE_NUM.put(31, "上海");
        ZONE_NUM.put(32, "江苏");
        ZONE_NUM.put(33, "浙江");
        ZONE_NUM.put(34, "安徽");
        ZONE_NUM.put(35, "福建");
        ZONE_NUM.put(36, "江西");
        ZONE_NUM.put(37, "山东");
        ZONE_NUM.put(41, "河南");
        ZONE_NUM.put(42, "湖北");
        ZONE_NUM.put(43, "湖南");
        ZONE_NUM.put(44, "广东");
        ZONE_NUM.put(45, "广西");
        ZONE_NUM.put(46, "海南");
        ZONE_NUM.put(50, "重庆");
        ZONE_NUM.put(51, "四川");
        ZONE_NUM.put(52, "贵州");
        ZONE_NUM.put(53, "云南");
        ZONE_NUM.put(54, "西藏");
        ZONE_NUM.put(61, "陕西");
        ZONE_NUM.put(62, "甘肃");
        ZONE_NUM.put(63, "青海");
        ZONE_NUM.put(64, "新疆");
        ZONE_NUM.put(71, "台湾");
        ZONE_NUM.put(81, "香港");
        ZONE_NUM.put(82, "澳门");
        ZONE_NUM.put(91, "外国");
    }

    private IdentityUtils() {
    }

    public static IdentityDescriptor parse(String number) {
        return SpringUtils.getBean(IdentityParser.class).parse(number).orElse(null);
    }

    public static boolean isMale(String number) {
        int n = number.length() == 18 ? Integer.parseInt(number.substring(16, 17)) : Integer.parseInt(number.substring(14, 15));
        return n % 2 == 1;
    }

    public static boolean isFemale(String number) {
        int n = number.length() == 18 ? Integer.parseInt(number.substring(16, 17)) : Integer.parseInt(number.substring(14, 15));
        return n % 2 == 0;
    }

    public static Date getDateOfBirth(String number) {
        try {
            String dob = number.length() == 18 ? number.substring(6, 14) : "19" + number.substring(6, 12);
            return DateUtils.parseDate(dob, "yyyyMMdd");
        } catch (ParseException e) {
            throw new IllegalArgumentException(e.getMessage(), e);
        }
    }

    private static int getYearOfBirth(String number) {
        try {
            String dob = number.length() == 18 ? number.substring(6, 14) : "19" + number.substring(6, 12);
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

    // -----------------------------------------------------------------------------------------------------------------

    public static String getProvince(String number) {
        return ZONE_NUM.get(Integer.parseInt(number.substring(0, 2)));
    }

}
