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

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

import java.lang.annotation.Annotation;

/**
 * @author 应卓
 * @see AnnotationAttributesHolder
 */
@Deprecated
public abstract class AbstractImportSelector implements ImportSelector {

    protected final AnnotationAttributes getAnnotationAttributes(AnnotationMetadata importingClassMetadata, Class<? extends Annotation> annotationType) {
        return AnnotationAttributes.fromMap(
                importingClassMetadata.getAnnotationAttributes(annotationType.getName(), false)
        );
    }

}
