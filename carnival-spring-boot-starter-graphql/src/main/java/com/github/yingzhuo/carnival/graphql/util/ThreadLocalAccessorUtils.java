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

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 应卓
 * @see org.springframework.graphql.execution.ThreadLocalAccessor
 * @since 1.10.16
 */
public final class ThreadLocalAccessorUtils {

    private ThreadLocalAccessorUtils() {
    }

    public static Map<String, Object> extract() {

        final List<ThreadLocalAccessor> accessors = SpringUtils.getBeanList(ThreadLocalAccessor.class);

        if (accessors.isEmpty()) {
            return Collections.emptyMap();
        }

        final Map<String, Object> map = new HashMap<>();
        accessors.forEach(accessor -> accessor.extractValues(map));
        return Collections.unmodifiableMap(map);
    }

    @SuppressWarnings("unchecked")
    public static <T> T extractObject(Class<T> objectType) {
        return (T) extract().get(objectType.getName());
    }

}
