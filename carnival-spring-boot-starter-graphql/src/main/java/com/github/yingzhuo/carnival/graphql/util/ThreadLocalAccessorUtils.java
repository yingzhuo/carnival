/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.graphql.util;

import com.github.yingzhuo.carnival.spring.SpringUtils;
import org.springframework.graphql.execution.ThreadLocalAccessor;

import java.util.*;

/**
 * @author 应卓
 * @see org.springframework.graphql.execution.ThreadLocalAccessor
 * @see org.springframework.graphql.execution.ThreadLocalAccessor#composite
 * @since 1.10.16
 */
@SuppressWarnings("unchecked")
public final class ThreadLocalAccessorUtils {

    private ThreadLocalAccessorUtils() {
    }

    public static ThreadLocalAccessor getAccessor() {
        final List<ThreadLocalAccessor> accessors = SpringUtils.getBeanList(ThreadLocalAccessor.class);
        if (accessors.isEmpty()) {
            return null;
        } else if (accessors.size() == 1) {
            return accessors.get(0);
        } else {
            return ThreadLocalAccessor.composite(accessors);
        }
    }

    public static Map<String, Object> extract() {
        final ThreadLocalAccessor accessor = getAccessor();
        if (accessor == null) {
            return Collections.emptyMap();
        }

        final Map<String, Object> map = new HashMap<>();
        accessor.extractValues(map);
        return Collections.unmodifiableMap(map);
    }

    public static <T> T extractObject(Class<T> objectType) {
        return extractObject(objectType, null);
    }

    public static <T> T extractObject(Class<T> objectType, T defaultIfNull) {
        return extractObject(objectType.getName(), defaultIfNull);
    }

    public static <T> T extractObject(String name) {
        return extractObject(name, null);
    }

    public static <T> T extractObject(String name, T defaultIfNull) {
        return Optional.ofNullable((T) extract().get(name)).orElse(defaultIfNull);
    }
}
