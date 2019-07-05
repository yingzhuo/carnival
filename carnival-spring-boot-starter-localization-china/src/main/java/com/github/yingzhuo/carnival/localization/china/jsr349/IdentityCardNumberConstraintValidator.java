/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.localization.china.jsr349;

import com.github.yingzhuo.carnival.localization.china.util.IdentityCardNumberUtils;
import org.apache.commons.lang3.time.DateUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Calendar;
import java.util.Date;

/**
 * @author 应卓
 */
public class IdentityCardNumberConstraintValidator implements ConstraintValidator<IdentityCardNumber, String> {

    private boolean compatibility15 = true;

    @Override
    public void initialize(IdentityCardNumber constraintAnnotation) {
        this.compatibility15 = constraintAnnotation.compatibility15();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        if (value == null) {
            return true;
        }

        int length = value.length();

        if ((length == 15 && compatibility15) || length == 18) {

            try {
                return doValid(value);
            } catch (Exception e) {
                return false;
            }
        }

        return false;
    }

    private final static int[] PARITYBIT = {'1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2'};
    private final static int[] POWER_LIST = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};

    private boolean doValid(String value) throws Exception {
        if (value.length() != 15 && value.length() != 18)
            return false;

        final char[] cs = value.toUpperCase().toCharArray();

        //校验位数
        int power = 0;
        for (int i = 0; i < cs.length; i++) {
            if (i == cs.length - 1 && cs[i] == 'X')
                break;//最后一位可以 是X或x
            if (cs[i] < '0' || cs[i] > '9')
                return false;
            if (i < cs.length - 1) {
                power += (cs[i] - '0') * POWER_LIST[i];
            }
        }

        //校验区位码
        if (IdentityCardNumberUtils.getProvince(value) == null) {
            return false;
        }

        //校验年份
        String year = value.length() == 15 ? getYearOfBirthAsString(value) : value.substring(6, 10);

        final int intYear = Integer.parseInt(year);
        if (intYear < 1900 || intYear > Calendar.getInstance().get(Calendar.YEAR)) {
            return false;   //1900年的PASS，超过今年的PASS
        }

        //校验月份
        String month = value.length() == 15 ? value.substring(8, 10) : value.substring(10, 12);
        final int intMonth = Integer.parseInt(month);
        if (intMonth < 1 || intMonth > 12) {
            return false;
        }

        //校验天数
        String day = value.length() == 15 ? value.substring(10, 12) : value.substring(12, 14);
        final int intDay = Integer.parseInt(day);
        if (intDay < 1 || intDay > 31) {
            return false;
        }

        //校验"校验码"
        if (value.length() == 15) { // 15位身份证无校检码
            return true;
        }

        return cs[cs.length - 1] == PARITYBIT[power % 11];
    }

    private String getYearOfBirthAsString(String certNo) throws Exception {
        String dob = certNo.substring(6, 12);
        Date date = DateUtils.parseDate(dob, "yyMMdd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return String.valueOf(cal.get(Calendar.YEAR));
    }

}
