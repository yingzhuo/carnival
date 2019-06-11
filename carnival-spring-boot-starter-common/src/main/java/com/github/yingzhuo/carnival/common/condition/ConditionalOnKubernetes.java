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
@Conditional(ConditionalOnKubernetes.OnConditionalOnKubernetes.class)
public @interface ConditionalOnKubernetes {

    public String[] value() default {"k8s", "kube", "kubernetes"};

    public static class OnConditionalOnKubernetes implements Condition {

        @Override
        @SuppressWarnings("ConstantConditions")
        public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
            val aas = AnnotationAttributes.fromMap(
                    metadata.getAnnotationAttributes(ConditionalOnKubernetes.class.getName()));
            val value = aas.getStringArray("value");

            if (value == null) {
                return false;
            }

            for (String v : value) {
                if (context.getEnvironment().acceptsProfiles(Profiles.of(v))) {
                    return true;
                }
            }
            return false;
        }
    }

}