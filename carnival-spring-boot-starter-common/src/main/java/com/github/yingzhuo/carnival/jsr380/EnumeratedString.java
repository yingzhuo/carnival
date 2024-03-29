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

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;
import java.util.Set;
import java.util.function.Supplier;

import static java.lang.annotation.ElementType.*;

/**
 * @author 应卓
 * @since 1.8.2
 */
@Documented
@Inherited
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EnumeratedStringValidator.class)
public @interface EnumeratedString {

    public Class<? extends EnumeratedString.Factory> value();

    public boolean caseSensitive() default true;

    public boolean cache() default true;

    public String message() default "{com.github.yingzhuo.carnival.jsr349.EnumeratedStringValidator.message}";

    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default {};

    @FunctionalInterface
    public static interface Factory extends Supplier<Set<String>> {
        @Override
        public Set<String> get();
    }

}
