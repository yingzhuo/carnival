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

import lombok.val;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author 应卓
 * @see SpringUtils
 * @since 1.6.20
 */
public final class EnvironmentUtils {

    private EnvironmentUtils() {
    }

    public static String resolvePlaceholders(String text) {
        if (text == null) return null;
        return SpringUtils.getEnvironment().resolvePlaceholders(text);
    }

    public static String getPropertyValue(String propertyName) {
        return getPropertyValue(propertyName, String.class);
    }

    public static <T> T getPropertyValue(String propertyName, Class<T> targetType) {
        return getPropertyValue(propertyName, targetType, null);
    }

    public static <T> T getPropertyValue(String propertyName, Class<T> targetType, T defaultIfNull) {
        T result = SpringUtils.getEnvironment().getProperty(propertyName, targetType);
        return result != null ? result : defaultIfNull;
    }

    public static List<String> getCommaDelimitedPropertyValue(String propertyName) {
        val value = getPropertyValue(propertyName);

        if (value == null) {
            return Collections.emptyList();
        } else {
            return Collections.unmodifiableList(Arrays.asList(StringUtils.commaDelimitedListToStringArray(value)));
        }
    }

}
