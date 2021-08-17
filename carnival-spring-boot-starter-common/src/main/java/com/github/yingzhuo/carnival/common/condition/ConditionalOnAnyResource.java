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

import com.github.yingzhuo.carnival.common.io.ResourceOptional;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.context.annotation.Conditional;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotatedTypeMetadata;

import java.io.IOException;
import java.lang.annotation.*;

/**
 * @author 应卓
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Conditional(ConditionalOnAnyResource.OnAnyResource.class)
public @interface ConditionalOnAnyResource {

    public String[] value();

    static final class OnAnyResource implements Condition {

        @Override
        public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
            final String[] locations = getLocations(metadata);

            try (ResourceOptional resourceOption = ResourceOptional.of(locations)) {
                return resourceOption.isPresent();
            } catch (IOException e) {
                return false;
            }
        }

        private String[] getLocations(AnnotatedTypeMetadata metadata) {
            try {
                final AnnotationAttributes attributes = AnnotationAttributes.fromMap(
                        metadata.getAnnotationAttributes(ConditionalOnAnyResource.class.getName()));
                if (attributes == null) return new String[0];
                return attributes.getStringArray("value");
            } catch (Exception e) {
                return new String[0];
            }
        }
    }

}
