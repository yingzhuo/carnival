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
 * @since 1.6.6
 */
public final class Unmodified {

    public static <T> Set<T> toSet(T... objs) {
        final Set<T> set = new HashSet<>();
        if (objs == null) {
            return Collections.emptySet();
        } else {
            Collections.addAll(set, objs);
        }
        return Collections.unmodifiableSet(set);
    }

    public static <E extends Enum<E>> Set<E> toSet(Class<E> type) {
        return Collections.unmodifiableSet(new HashSet<>(Arrays.asList(type.getEnumConstants())));
    }

    public static <T> List<T> toList(T... objs) {
        if (objs == null) {
            return Collections.emptyList();
        }
        return Collections.unmodifiableList(Arrays.asList(objs));
    }

    public static <E extends Enum<E>> List<E> toList(Class<E> type) {
        return Collections.unmodifiableList(Arrays.asList(type.getEnumConstants()));
    }

    // ----------------------------------------------------------------------------------------------------------------

    private Unmodified() {
    }

}
