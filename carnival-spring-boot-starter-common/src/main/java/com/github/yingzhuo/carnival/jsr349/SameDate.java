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

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @author 应卓
 * @since 1.9.7
 */
@Repeatable(SameDate.List.class)
@Documented
@Inherited
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = SameDateValidator.class)
public @interface SameDate {

    public String message() default "{com.github.yingzhuo.carnival.jsr349.SameDate.message}";

    public String field();

    public String fieldMatch();

    public boolean errorIfNull() default true;

    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default {};

    @Inherited
    @Documented
    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    @interface List {
        SameDate[] value();
    }

}
