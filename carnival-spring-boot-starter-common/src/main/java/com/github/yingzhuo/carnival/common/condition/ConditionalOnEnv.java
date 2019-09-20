/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.common.condition;

import lombok.val;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.context.annotation.Conditional;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotatedTypeMetadata;

import java.lang.annotation.*;

/**
 * @author 应卓
 * @since 1.1.9
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Conditional(ConditionalOnEnv.OnEnv.class)
public @interface ConditionalOnEnv {

    public String name();

    public String val();

    public boolean ignoreCase() default false;

    public static class OnEnv implements Condition {

        @Override
        public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {

            val aas = AnnotationAttributes.fromMap(
                    metadata.getAnnotationAttributes(ConditionalOnEnv.class.getName()));

            val name = aas.getString("name");
            val value = aas.getString("val");
            val ignoreCase = aas.getBoolean("ignoreCase");

            if (ignoreCase) {
                return StringUtils.equalsIgnoreCase(System.getenv(name), value);
            } else {
                return StringUtils.equals(System.getenv(name), value);
            }
        }
    }

}
