/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.jpa.util;

import java.util.*;

/**
 * @author 应卓
 * @since 1.10.22
 */
public final class Enums {

    private Enums() {
    }

    public static <T extends Enum<T>> Set<T> getAllIfNullAsSet(T e, Class<T> type) {
        return new HashSet<>(getAllIfNullAsList(e, type));
    }

    public static <T extends Enum<T>> Set<T> getAllIfNullAsSet(Set<T> es, Class<T> type) {
        if (es == null || es.isEmpty()) {
            return getAllIfNullAsSet((T) null, type);
        } else {
            return es;
        }
    }

    public static <T extends Enum<T>> List<T> getAllIfNullAsList(T e, Class<T> type) {
        if (e == null) {
            return Arrays.asList(type.getEnumConstants());
        } else {
            final List<T> newList = new ArrayList<>(1);
            newList.add(e);
            return newList;
        }
    }

    public static <T extends Enum<T>> List<T> getAllIfNullAsList(List<T> es, Class<T> type) {
        if (es == null || es.isEmpty()) {
            return getAllIfNullAsList((T) null, type);
        } else {
            return es;
        }
    }

    public static <T extends Enum<T>> Collection<T> getAllIfNullAsCollection(T e, Class<T> type) {
        return getAllIfNullAsList(e, type);
    }

    public static <T extends Enum<T>> Collection<T> getAllIfNullAsCollection(Collection<T> es, Class<T> type) {
        if (es == null || es.isEmpty()) {
            return getAllIfNullAsCollection((T) null, type);
        } else {
            return es;
        }
    }

}
