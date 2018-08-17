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

    public String message() default "Invalid password.";

    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default {};

    @Documented
    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    @interface List {
        FieldsValueMatch[] value();
    }

    // ----------------------------------------------------------------------------------------------------------------

    /**
     * 密码验证的复杂度
     */
    public enum Complexity {

        /**
         * 无要求
         */
        NONE,

        /**
         * 数字 (0-9)
         */
        NUMERIC,

        /**
         * 字母 (a-z, A-Z)
         */
        ALPHABETIC,

        /**
         * 字母 + 数字 (a-z, A-Z, 0-9)
         */
        ALPHABETIC_AND_NUMERIC,

        /**
         * 字母 + 数字 + 特殊字符
         */
        ALPHABETIC_AND_NUMERIC_AND_SPECIAL_CHARS

    }
}
