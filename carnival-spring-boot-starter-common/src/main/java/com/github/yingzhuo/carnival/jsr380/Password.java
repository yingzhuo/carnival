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

import static java.lang.annotation.ElementType.*;

/**
 * @author 应卓
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Constraint(validatedBy = PasswordValidator.class)
public @interface Password {

    public Complexity complexity() default Complexity.ANY;

    public String specialChars() default "\"',./<>?;:'{}[]+=-_!@#$%^&*()`~";

    public int min() default Integer.MIN_VALUE;

    public int max() default Integer.MAX_VALUE;

    public String message() default "{com.github.yingzhuo.carnival.jsr349.Password.message}";

    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default {};

    // ----------------------------------------------------------------------------------------------------------------

    /**
     * 密码的复杂度
     */
    public enum Complexity {

        /**
         * 无要求
         */
        ANY,

        /**
         * 包含数字 (0-9)
         */
        HAS_NUMERIC,

        /**
         * 包含且只包含数字 (0-9)
         */
        ONLY_NUMERIC,

        /**
         * 包含字母 (a-z, A-Z)
         */
        HAS_ALPHABETIC,

        /**
         * 包含且只包含字母 (a-z, A-Z)
         */
        ONLY_ALPHABETIC,

        /**
         * 字母 + 数字 (a-z, A-Z, 0-9)
         */
        ALPHABETIC_AND_NUMERIC,

        /**
         * 字母 + 数字 + 特殊字符
         */
        ALPHABETIC_AND_NUMERIC_AND_SPECIAL_CHARS,

        /**
         * 小写字母 + 大写字母 + 数字
         */
        LOWER_AND_UPPER_AND_NUMERIC,

        /**
         * 小写字母 + 大写字母 + 数字 + 特殊字符
         */
        LOWER_AND_UPPER_AND_NUMERIC_AND_SPECIAL_CHARS,

        /**
         * 字母 数字 特殊字符 中至少两种
         */
        AT_LEAST_TWO_KIND_OF_ALPHABETIC_AND_NUMERIC_AND_SPECIAL_CHARS
    }

}
