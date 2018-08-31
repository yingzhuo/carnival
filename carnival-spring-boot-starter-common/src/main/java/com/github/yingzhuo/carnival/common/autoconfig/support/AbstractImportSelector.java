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
import java.util.HashMap;
import java.util.Map;

/**
 * @author 应卓
 */
public abstract class AbstractImportSelector implements ImportSelector {

    public static final ThreadLocal<Map<String, Object>> HOLDER = ThreadLocal.withInitial(HashMap::new);

    public static void putConfig(String key, Object value) {
        HOLDER.get().put(key, value);
    }

    @SuppressWarnings("unchecked")
    public static <T> T getConfig(String key, Class<T> type) {
        return (T) HOLDER.get().get(key);
    }

    public static <T> T getConfig(String key, Class<T> type, T defaultValue) {
        T value = getConfig(key, type);

        if (value == null)
            return defaultValue;
        else
            return value;
    }

    public static void clearConfigs() {
        HOLDER.get().clear();
    }

    protected final AnnotationAttributes getAnnotationAttributes(AnnotationMetadata importingClassMetadata, Class<? extends Annotation> annotationType) {
        return AnnotationAttributes.fromMap(
                importingClassMetadata.getAnnotationAttributes(annotationType.getName(), false)
        );
    }

}
