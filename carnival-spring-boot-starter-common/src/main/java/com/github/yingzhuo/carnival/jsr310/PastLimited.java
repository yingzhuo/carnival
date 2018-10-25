package com.github.yingzhuo.carnival.jsr310;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * @author 应卓
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.FIELD})
@Constraint(validatedBy = PastLimitedConstraintValidator.class)
public @interface PastLimited {

    public long value();

    public TimeUnit timeUnit() default TimeUnit.DAYS;

    public DateTruncation dateTruncation() default DateTruncation.DATE;

    public String message() default "Invalid file";

    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default {};

    @Documented
    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    @interface List {
        PastLimited[] value();
    }

}
