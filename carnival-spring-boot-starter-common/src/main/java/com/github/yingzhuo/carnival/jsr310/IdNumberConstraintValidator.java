/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.jsr310;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author 应卓
 */
@Slf4j
public class IdNumberConstraintValidator implements ConstraintValidator<IdNumber, String> {

    // TODO: 参考 https://segmentfault.com/a/1190000013737958

    private static final int[] FACTOR = new int[]{7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};
    private static final String[] PARITY = new String[]{"1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2"};

    private static final Map<Integer, String> REGION_MAP = new HashMap<>();

    static {
        REGION_MAP.put(11, "北京");
        REGION_MAP.put(12, "天津");
        REGION_MAP.put(13, "河北");
        REGION_MAP.put(14, "山西");
        REGION_MAP.put(15, "内蒙古");
        REGION_MAP.put(21, "辽宁");
        REGION_MAP.put(22, "吉林");
        REGION_MAP.put(23, "黑龙江");
        REGION_MAP.put(31, "上海");
        REGION_MAP.put(32, "江苏");
        REGION_MAP.put(33, "浙江");
        REGION_MAP.put(34, "安徽");
        REGION_MAP.put(35, "福建");
        REGION_MAP.put(36, "江西");
        REGION_MAP.put(37, "山东");
        REGION_MAP.put(41, "河南");
        REGION_MAP.put(42, "湖北");
        REGION_MAP.put(43, "湖南");
        REGION_MAP.put(44, "广东");
        REGION_MAP.put(45, "广西");
        REGION_MAP.put(46, "海南");
        REGION_MAP.put(50, "重庆");
        REGION_MAP.put(51, "四川");
        REGION_MAP.put(52, "贵州");
        REGION_MAP.put(53, "云南");
        REGION_MAP.put(54, "西藏");
        REGION_MAP.put(61, "陕西");
        REGION_MAP.put(62, "甘肃");
        REGION_MAP.put(63, "青海");
        REGION_MAP.put(64, "宁夏");
        REGION_MAP.put(65, "新疆");
        REGION_MAP.put(71, "台湾");
        REGION_MAP.put(81, "香港");
        REGION_MAP.put(82, "澳门");
    }

    private static final Pattern PATTERN = Pattern.compile("^[0-9]{17}[0-9xX]$");

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) return true;

        final Matcher matcher = PATTERN.matcher(value);
        return matcher.matches() &&
                isValidCheckCode(value) &&
                isValidDob(value) &&
                isValidRegionCode(value);
    }

    // 校检码
    private boolean isValidCheckCode(String idNumber) {
        final String s17 = idNumber.substring(0, 17);

        int sum = 0;
        for (int i = 0; i < 17; i++) {
            int current = Integer.valueOf(s17.substring(i, i + 1));
            sum += current * FACTOR[i];
        }

        final String test = PARITY[sum % 11];
        return idNumber.substring(17, 18).equalsIgnoreCase(test);
    }

    private boolean isValidRegionCode(String idNumber) {
        try {
            final int code = Integer.valueOf(idNumber.substring(0, 2));
            return REGION_MAP.keySet().contains(code);
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isValidDob(String idNumber) {
        final String s = idNumber.substring(6, 14);
        try {
            DateUtils.parseDate(s, "yyyyMMdd");
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
}
