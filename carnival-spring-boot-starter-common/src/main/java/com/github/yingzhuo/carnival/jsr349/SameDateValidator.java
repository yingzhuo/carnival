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

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.BeanWrapperImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Date;

/**
 * @author 应卓
 * @since 1.9.7
 */
public class SameDateValidator implements ConstraintValidator<SameDate, Object> {

    private String field;
    private String fieldMatch;
    private boolean errorIfNull;

    @Override
    public void initialize(SameDate annotation) {
        this.field = annotation.field();
        this.fieldMatch = annotation.fieldMatch();
        this.errorIfNull = annotation.errorIfNull();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) return true;
        Object fieldValue = new BeanWrapperImpl(value).getPropertyValue(field);
        Object fieldMatchValue = new BeanWrapperImpl(value).getPropertyValue(fieldMatch);
        if (fieldValue == null || fieldMatchValue == null) {
            return !errorIfNull;
        }
        return DateUtils.isSameDay((Date) fieldValue, (Date) fieldMatchValue);
    }

}
