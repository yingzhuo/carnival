/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.common.util;

import java.util.*;

/**
 * @author 应卓
 */
@Deprecated
public final class Validate {

    public static <T> T notNull(T obj) {
        if (obj == null) {
            throw new NullPointerException();
        }
        return obj;
    }

    public static <T> T[] notEmpty(T[] array) {
        notNull(array);
        if (array.length == 0) {
            throw new IllegalArgumentException();
        }
        return array;
    }

    public static <T> Collection<T> notEmpty(Collection<T> collection) {
        notNull(collection);
        if (collection.isEmpty()) {
            throw new IllegalArgumentException();
        }
        return collection;
    }

    public static <T> List<T> notEmpty(List<T> list) {
        notNull(list);
        if (list.isEmpty()) {
            throw new IllegalArgumentException();
        }
        return list;
    }

    public static <T> Set<T> notEmpty(Set<T> set) {
        notNull(set);
        if (set.isEmpty()) {
            throw new IllegalArgumentException();
        }
        return set;
    }

    public static <T> SortedSet<T> notEmpty(SortedSet<T> sortedSet) {
        notNull(sortedSet);
        if (sortedSet.isEmpty()) {
            throw new IllegalArgumentException();
        }
        return sortedSet;
    }

    public static <K, V> Map<K, V> notEmpty(Map<K, V> map) {
        notNull(map);
        if (map.isEmpty()) {
            throw new IllegalArgumentException();
        }
        return map;
    }

    public static <K, V> SortedMap<K, V> notEmpty(SortedMap<K, V> sortedMap) {
        notNull(sortedMap);
        if (sortedMap.isEmpty()) {
            throw new IllegalArgumentException();
        }
        return sortedMap;
    }

    public static <T> Optional<T> notEmpty(Optional<T> optional) {
        notNull(optional);
        if (!optional.isPresent()) {
            throw new IllegalArgumentException();
        }
        return optional;
    }

    public static <T extends CharSequence> T notEmpty(T chars) {
        notNull(chars);
        if (chars.toString().isEmpty()) {
            throw new IllegalArgumentException();
        }
        return chars;
    }

    public static <T extends CharSequence> T notBlank(T chars) {
        notNull(chars);
        if (chars.chars().allMatch(ch -> Character.isWhitespace((char) ch))) {
            throw new IllegalArgumentException();
        }
        return chars;
    }

    // -----------------------------------------------------------------------------------------------------------------

    private Validate() {
    }

}
