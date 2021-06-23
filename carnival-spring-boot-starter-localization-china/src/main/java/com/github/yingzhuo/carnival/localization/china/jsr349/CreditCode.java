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
import java.lang.annotation.*;

/**
 * 检查社会信用代码工具
 *
 * @author 应卓
 * @since 1.9.6
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Constraint(validatedBy = CreditCodeConstraintValidator.class)
public @interface CreditCode {

    public String message() default "{com.github.yingzhuo.carnival.localization.china.jsr349.CreditCode.message}";

    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default {};

}
