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
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.context.annotation.Conditional;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.env.Profiles;
import org.springframework.core.type.AnnotatedTypeMetadata;

import java.lang.annotation.*;

/**
 * @author 应卓
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Conditional(ConditionalOnDebugMode.OnDebugMode.class)
public @interface ConditionalOnDebugMode {

    public String debugProfile() default "debug";

    static final class OnDebugMode implements Condition {

        @Override
        @SuppressWarnings("ConstantConditions")
        public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
            val aas = AnnotationAttributes.fromMap(
                    metadata.getAnnotationAttributes(ConditionalOnDebugMode.class.getName()));
            val value = aas.getString("debugProfile");

            if (value == null) {
                return false;
            }

            return context.getEnvironment().acceptsProfiles(Profiles.of(value));
        }
    }

}
