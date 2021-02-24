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
 * @since 1.7.9
 */
@Repeatable(FieldsValueSorted.List.class)
@Documented
@Inherited
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FieldsValueSortedValidator.class)
public @interface FieldsValueSorted {

    public String message() default "{com.github.yingzhuo.carnival.jsr349.FieldsValueSorted.message}";

    public String fieldBefore();

    public String fieldAfter();

    public boolean equalsAsOk() default true;

    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default {};

    @Inherited
    @Documented
    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    @interface List {
        FieldsValueSorted[] value();
    }

}
