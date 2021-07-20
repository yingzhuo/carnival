/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.spring;

import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

import java.lang.annotation.Annotation;
import java.util.Objects;

/**
 * @author 应卓
 * @since 1.9.14
 */
public final class AnnotationAttributesUtils {

    private AnnotationAttributesUtils() {
    }

    public static <A extends Annotation> AnnotationAttributes toAnnotationAttributes(AnnotationMetadata metadata, Class<A> annotationType) {
        Objects.requireNonNull(metadata);
        return AnnotationAttributes.fromMap(metadata.getAnnotationAttributes(annotationType.getName()));
    }

}
