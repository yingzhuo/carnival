/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.spring.boot.validation.jsr310;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author 应卓
 */
public class UUID32Validator implements ConstraintValidator<UUID32, String> {

    private static final Pattern PATTERN = Pattern.compile("^[0-9a-z]{32}$");

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        Matcher matcher = PATTERN.matcher(value);
        return matcher.matches();
    }

}
