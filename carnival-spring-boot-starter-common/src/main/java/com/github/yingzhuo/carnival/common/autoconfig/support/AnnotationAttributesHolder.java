/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.common.autoconfig.support;

import lombok.val;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * carnival内部工具
 *
 * @author 应卓
 */
@SuppressWarnings("unchecked")
public final class AnnotationAttributesHolder {

    private AnnotationAttributesHolder() {
    }

    private static final ThreadLocal<Map<String, AnnotationAttributes>> HOLDER = ThreadLocal.withInitial(HashMap::new);

    public static <A extends Annotation> void setAnnotationMetadata(Class<A> annotationType, AnnotationMetadata importingClassMetadata) {
        Objects.requireNonNull(annotationType);
        Objects.requireNonNull(importingClassMetadata);

        val key = annotationType.getName();
        val annotationAttributes = AnnotationAttributes.fromMap(
                importingClassMetadata.getAnnotationAttributes(key, false));

        HOLDER.get().put(key, annotationAttributes);
    }

    public static <A extends Annotation, V> V getValue(Class<A> annotationType, String name) {
        Objects.requireNonNull(annotationType);
        Objects.requireNonNull(name);

        val key = annotationType.getName();
        val annotationAttributes = HOLDER.get().get(key);

        if (annotationAttributes == null) {
            return null;
        } else {
            return (V) annotationAttributes.get(name);
        }
    }

    public static <A extends Annotation, V> V getValue(Class<A> annotationType, String name, V defaultIfNull) {
        val result = getValue(annotationType, name);
        return result != null ? (V) result : defaultIfNull;
    }

}
