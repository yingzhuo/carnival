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

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author 应卓
 */
public class PhoneNumberValidator implements ConstraintValidator<PhoneNumber, String> {

    // 2018-06-13 支持号段
    // 参考: https://blog.csdn.net/voidmain_123/article/details/78962164
    private static final Pattern PHONE_NUMBER_REG = Pattern.compile("^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\\d{8}$");

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        if (value == null) return true;

        if (value.length() != 11) {
            return false;
        }

        Matcher matcher = PHONE_NUMBER_REG.matcher(value);
        return matcher.matches();
    }

}
