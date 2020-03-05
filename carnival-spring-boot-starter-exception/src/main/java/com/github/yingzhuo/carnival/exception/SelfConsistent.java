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

    public static <T> T notNull(T obj, String message) {
        if (obj == null) {
            throw new SelfConsistentException(message);
        }
        return obj;
    }

    public static <T> void equals(T obj1, T obj2) {
        equals(obj1, obj2, null);
    }

    public static <T> void equals(T obj1, T obj2, String message) {
        if (!Objects.equals(obj1, obj2)) {
            throw new SelfConsistentException(message);
        }
    }

    public static <T> void notEquals(T obj1, T obj2) {
        notEquals(obj1, obj2, null);
    }

    public static <T> void notEquals(T obj1, T obj2, String message) {
        if (Objects.equals(obj1, obj2)) {
            throw new SelfConsistentException(message);
        }
    }

    public static <T> Collection<T> notEmpty(Collection<T> collection) {
        return notEmpty(collection, null);
    }

    public static <T> Collection<T> notEmpty(Collection<T> collection, String message) {
        notNull(collection, message);
        if (collection.isEmpty()) {
            throw new SelfConsistentException(message);
        }
        return collection;
    }

    public static <T> T[] notEmpty(T[] array) {
        return notEmpty(array, null);
    }

    public static <T> T[] notEmpty(T[] array, String message) {
        notNull(array, message);
        if (array.length == 0) {
            throw new SelfConsistentException(message);
        }
        return array;
    }

    public static <K, V> Map<K, V> notEmpty(Map<K, V> map) {
        return notEmpty(map, null);
    }

    public static <K, V> Map<K, V> notEmpty(Map<K, V> map, String message) {
        notNull(map, message);
        if (map.isEmpty()) {
            throw new SelfConsistentException(message);
        }
        return map;
    }

    public static <T extends CharSequence> T notEmpty(T string) {
        return notEmpty(string, null);
    }

    public static <T extends CharSequence> T notEmpty(T string, String message) {
        if (StringUtils.isEmpty(string)) {
            throw new SelfConsistentException(message);
        }
        return string;
    }

    public static <T extends CharSequence> T notBlank(T string) {
        return notBlank(string, null);
    }

    public static <T extends CharSequence> T notBlank(T string, String message) {
        if (StringUtils.isBlank(string)) {
            throw new SelfConsistentException(message);
        }
        return string;
    }

    public static <T> void noNullElement(Collection<T> collection) {
        noNullElement(collection, null);
    }

    public static <T> void noNullElement(Collection<T> collection, String message) {
        notNull(collection, message);
        boolean noNull = collection.stream().noneMatch(Objects::isNull);
        if (!noNull) {
            throw new SelfConsistentException(message);
        }
    }

    public static <T> void noNullElement(T[] array) {
        notNull(array, null);
    }

    public static <T> void noNullElement(T[] array, String message) {
        notNull(array, message);
        boolean noNull = Arrays.stream(array).noneMatch(Objects::isNull);
        if (!noNull) {
            throw new SelfConsistentException(message);
        }
    }

    // -----------------------------------------------------------------------------------------------------------------

    private SelfConsistent() {
    }

}
