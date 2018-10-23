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
public class IdNumberConstraintValidator implements ConstraintValidator<IdNumber, String> {

    // TODO: 参考 https://segmentfault.com/a/1190000013737958

    private static final Pattern PATTERN = Pattern.compile("^[0-9]{17}[0-9xX]$");

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) return true;

        final Matcher matcher = PATTERN.matcher(value);
        return matcher.matches();
    }

}
