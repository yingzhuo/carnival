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

import lombok.val;
import lombok.var;
import org.apache.commons.lang3.time.DateUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Date;

/**
 * @author 应卓
 */
public class PastLimitedConstraintValidator implements ConstraintValidator<PastLimited, Date> {

    private PastLimited annotation;

    @Override
    public boolean isValid(Date value, ConstraintValidatorContext context) {
        var now = new Date();

        if (annotation.dateTruncation().getValue() != -1) {
            now = DateUtils.truncate(now, annotation.dateTruncation().getValue());
        }

        val mills = annotation.timeUnit().toMillis(annotation.value());

        return value.getTime() >= now.getTime() - mills;
    }

    @Override
    public void initialize(PastLimited constraintAnnotation) {
        this.annotation = constraintAnnotation;
    }

}
