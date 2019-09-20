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
 * @since 1.1.9
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Conditional(ProfileAll.OnProfileAllCondition.class)
public @interface ProfileAll {

    public String[] value();

    public static class OnProfileAllCondition implements Condition {

        @Override
        @SuppressWarnings("ConstantConditions")
        public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
            val aas = AnnotationAttributes.fromMap(
                    metadata.getAnnotationAttributes(ProfileAll.class.getName()));
            val value = aas.getStringArray("value");

            if (value == null) {
                return false;
            }

            for (String v : value) {
                if (!context.getEnvironment().acceptsProfiles(Profiles.of(v))) {
                    return false;
                }
            }

            return true;
        }
    }

}
