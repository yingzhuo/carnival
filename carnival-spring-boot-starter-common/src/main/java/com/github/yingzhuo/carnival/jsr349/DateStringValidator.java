/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.jsr349;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * @author 应卓
 * @since 1.7.7
 */
public class DateStringValidator implements ConstraintValidator<DateString, CharSequence> {

    private DateString annotation;

    @Override
    public void initialize(DateString constraintAnnotation) {
        this.annotation = constraintAnnotation;
    }

    @Override
    public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
        final DateFormat dateFormat = new SimpleDateFormat(annotation.pattern());
        try {
            dateFormat.parse(value.toString());
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

}
