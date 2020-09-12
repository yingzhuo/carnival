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

import org.springframework.beans.BeanWrapperImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author 应卓
 * @since 1.7.9
 */
public class FieldsValueSortedValidator implements ConstraintValidator<FieldsValueSorted, Object> {

    private String before;
    private String after;
    private boolean equalsAsOk;

    @Override
    public void initialize(FieldsValueSorted constraintAnnotation) {
        this.before = constraintAnnotation.fieldBefore();
        this.after = constraintAnnotation.fieldAfter();
        this.equalsAsOk = constraintAnnotation.equalsAsOk();
    }

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        Object fieldBefore = new BeanWrapperImpl(value).getPropertyValue(before);
        Object fieldAfter = new BeanWrapperImpl(value).getPropertyValue(after);

        if ((fieldBefore instanceof Comparable) && (fieldAfter instanceof Comparable)) {

            final int c = ((Comparable) fieldBefore).compareTo(fieldAfter);
            if (c == 0) return equalsAsOk;
            return c < 0;
        }
        return false;
    }

}
