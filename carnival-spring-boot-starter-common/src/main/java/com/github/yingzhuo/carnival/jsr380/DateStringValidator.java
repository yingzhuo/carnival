/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.jsr380;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * @author 应卓
 * @since 1.7.7
 */
public class DateStringValidator implements ConstraintValidator<DateString, CharSequence> {

    private DateTimeFormatter formatter;

    @Override
    public void initialize(DateString constraintAnnotation) {
        this.formatter = DateTimeFormatter.ofPattern(constraintAnnotation.pattern());
    }

    @Override
    public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
        if (value == null) return true;

        try {
            formatter.parse(value);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

}
