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

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.context.annotation.Conditional;
import org.springframework.core.type.AnnotatedTypeMetadata;

import java.lang.annotation.*;
import java.util.Map;

/**
 * @author 应卓
 * @since 1.6.20
 */
@Documented
@Inherited
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Conditional(ConditionalOnEnvironment.OnEnv.class)
public @interface ConditionalOnEnvironment {

    public String name();

    public String value();

    public static class OnEnv implements Condition {
        @Override
        public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
            final Map<String, Object> attrs = metadata.getAnnotationAttributes(ConditionalOnEnvironment.class.getName(), false);
            final String envName = (String) attrs.get("name");
            final String envValue = (String) attrs.get("value");

            try {
                return context.getEnvironment().resolveRequiredPlaceholders(envName).equals(envValue);
            } catch (IllegalArgumentException e) {
                return false;
            }
        }
    }

}
