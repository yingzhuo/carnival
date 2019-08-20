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

import com.github.yingzhuo.carnival.common.time.DateTruncation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * @author 应卓
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
@Constraint(validatedBy = AroundCurrentTimeMatchValidator.class)
public @interface AroundCurrentTime {

    public int past() default -1;

    public int future() default -1;

    public TimeUnit timeUnit() default TimeUnit.DAYS;

    public DateTruncation dateTruncation() default DateTruncation.DAY;

    public String message() default "Invalid date.";

    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default {};

}
