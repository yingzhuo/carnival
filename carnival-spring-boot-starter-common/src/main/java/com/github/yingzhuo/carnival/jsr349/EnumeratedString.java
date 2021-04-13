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
 * @since 1.8.2
 */
@Documented
@Inherited
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EnumeratedStringValidator.class)
public @interface EnumeratedString {

    public String[] value();

    public boolean caseSensitive() default true;

    public String message() default "{com.github.yingzhuo.carnival.jsr349.EnumeratedStringValidator.message}";

    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default {};

}
