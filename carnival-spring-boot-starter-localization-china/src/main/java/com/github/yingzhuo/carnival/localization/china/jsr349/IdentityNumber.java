/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.localization.china.jsr349;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

/**
 * 检查身份证号码
 *
 * @author 应卓
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Constraint(validatedBy = IdentityNumberConstraintValidator.class)
public @interface IdentityNumber {

    public String message() default "{com.github.yingzhuo.carnival.localization.china.jsr349.IdentityNumber.message}";

    /**
     * 兼容15位老身份证号码
     *
     * @return true表示兼容
     */
    public boolean compatibility15() default true;

    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default {};

}
