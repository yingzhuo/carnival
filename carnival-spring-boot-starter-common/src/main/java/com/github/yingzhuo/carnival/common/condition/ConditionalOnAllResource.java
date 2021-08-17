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

import com.github.yingzhuo.carnival.spring.ResourceUtils;
import lombok.val;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.context.annotation.Conditional;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotatedTypeMetadata;

import java.lang.annotation.*;

/**
 * @author 应卓
 * @see org.springframework.boot.autoconfigure.condition.ConditionalOnResource
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Conditional(ConditionalOnAllResource.OnAllResource.class)
public @interface ConditionalOnAllResource {

    public String[] value();

    static final class OnAllResource implements Condition {

        @Override
        public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
            final String[] locations = getLocations(metadata);

            for (String location : locations) {
                try {
                    val resource = ResourceUtils.loadResource(location);
                    if (!resource.exists()) {
                        return false;
                    }
                } catch (Exception e) {
                    return false;
                }
            }

            return true;
        }

        private String[] getLocations(AnnotatedTypeMetadata metadata) {
            try {
                final AnnotationAttributes attributes = AnnotationAttributes.fromMap(
                        metadata.getAnnotationAttributes(ConditionalOnAllResource.class.getName()));
                if (attributes == null) return new String[0];
                return attributes.getStringArray("value");
            } catch (Exception e) {
                return new String[0];
            }
        }
    }

}
