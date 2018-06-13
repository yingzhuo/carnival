/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.spring.boot.validation.jsr310;

import com.github.yingzhuo.spring.boot.validation.jsr310.password.Complexity;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @author 应卓
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.FIELD})
@Constraint(validatedBy = PasswordValidator.class)
public @interface Password {

    public Complexity complexity() default Complexity.ALPHABETIC_AND_NUMERIC_AND_SPECIAL_CHARS;

    public String specialChars() default "\"',./<>?;:'{}[]+=-_!@#$%^&*()`~ ";

    public int minLength() default 6;

    public int maxLength() default 12;

    public String message() default "Invalid password";

    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default {};

}
