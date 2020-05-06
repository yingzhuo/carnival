/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.exception;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;

import static com.github.yingzhuo.carnival.common.util.MessageFormatter.format;

/**
 * 逻辑自洽性
 *
 * @author 应卓
 * @since 1.4.5
 */
public final class SelfConsistent {

    public static <T> T notNull(T obj) {
        return notNull(obj, null);
    }

    public static <T> T notNull(T obj, String message, Object... params) {
        if (obj == null) {
            throw new SelfConsistentException(format(message, params));
        }
        return obj;
    }

    public static <T> void equals(T obj1, T obj2) {
        equals(obj1, obj2, null);
    }

    public static <T> void equals(T obj1, T obj2, String message, Object... params) {
        if (!Objects.equals(obj1, obj2)) {
            throw new SelfConsistentException(format(message, params));
        }
    }

    public static <T> void notEquals(T obj1, T obj2) {
        notEquals(obj1, obj2, null);
    }

    public static <T> void notEquals(T obj1, T obj2, String message, Object... params) {
        if (Objects.equals(obj1, obj2)) {
            throw new SelfConsistentException(format(message, params));
        }
    }

    public static <T> Collection<T> notEmpty(Collection<T> collection) {
        return notEmpty(collection, null);
    }

    public static <T> Collection<T> notEmpty(Collection<T> collection, String message, Object... params) {
        notNull(collection, message, params);
        if (collection.isEmpty()) {
            throw new SelfConsistentException(format(message, params));
        }
        return collection;
    }

    public static <T> T[] notEmpty(T[] array) {
        return notEmpty(array, null);
    }

    public static <T> T[] notEmpty(T[] array, String message, Object... params) {
        notNull(array, message, params);
        if (array.length == 0) {
            throw new SelfConsistentException(format(message, params));
        }
        return array;
    }

    public static <K, V> Map<K, V> notEmpty(Map<K, V> map) {
        return notEmpty(map, null);
    }

    public static <K, V> Map<K, V> notEmpty(Map<K, V> map, String message, Object... params) {
        notNull(map, message, params);
        if (map.isEmpty()) {
            throw new SelfConsistentException(format(message, params));
        }
        return map;
    }

    public static <T extends CharSequence> T notEmpty(T string) {
        return notEmpty(string, null);
    }

    public static <T extends CharSequence> T notEmpty(T string, String message, Object... params) {
        if (StringUtils.isEmpty(string)) {
            throw new SelfConsistentException(format(message, params));
        }
        return string;
    }

    public static <T extends CharSequence> T notBlank(T string) {
        return notBlank(string, null);
    }

    public static <T extends CharSequence> T notBlank(T string, String message, Object... params) {
        if (StringUtils.isBlank(string)) {
            throw new SelfConsistentException(format(message, params));
        }
        return string;
    }

    public static <T> void noNullElement(Collection<T> collection) {
        noNullElement(collection, null);
    }

    public static <T> void noNullElement(Collection<T> collection, String message, Object... params) {
        notNull(collection, message, params);
        boolean noNull = collection.stream().noneMatch(Objects::isNull);
        if (!noNull) {
            throw new SelfConsistentException(format(message, params));
        }
    }

    public static <T> void noNullElement(T[] array) {
        notNull(array, null);
    }

    public static <T> void noNullElement(T[] array, String message, Object... params) {
        notNull(array, message, params);
        boolean noNull = Arrays.stream(array).noneMatch(Objects::isNull);
        if (!noNull) {
            throw new SelfConsistentException(format(message, params));
        }
    }

    public static void isTrue(boolean state) {
        isTrue(state, null);
    }

    public static void isTrue(boolean state, String message, Object... params) {
        if (!state) {
            throw new SelfConsistentException(format(message, params));
        }
    }

    public static void isFalse(boolean state) {
        isFalse(state, null);
    }

    public static void isFalse(boolean state, String message, Object... params) {
        if (state) {
            throw new SelfConsistentException(format(message, params));
        }
    }

    // -----------------------------------------------------------------------------------------------------------------

    private SelfConsistent() {
    }

}
