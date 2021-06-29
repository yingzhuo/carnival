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
public class NotSameDateValidator implements ConstraintValidator<NotSameDate, Date> {

    private String field;
    private String fieldMatch;

    @Override
    public void initialize(NotSameDate annotation) {
        this.field = annotation.field();
        this.fieldMatch = annotation.fieldMatch();
    }

    @Override
    public boolean isValid(Date value, ConstraintValidatorContext context) {
        if (value == null) return true;
        Object fieldValue = new BeanWrapperImpl(value).getPropertyValue(field);
        Object fieldMatchValue = new BeanWrapperImpl(value).getPropertyValue(fieldMatch);
        return !DateUtils.isSameDay((Date) fieldValue, (Date) fieldMatchValue);
    }

}
