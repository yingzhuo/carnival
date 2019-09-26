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

import java.util.HashMap;
import java.util.Map;

/**
 * (内部工具，不提供给Carnival的用户使用)
 *
 * @author 应卓
 * @since 1.1.13
 */
public final class GlobalHolder {

    // 并非线程安全
    private static final Map<String, Object> MAP = new HashMap<>();

    public static void put(String key, Object value) {
        if (MAP.get(key) != null) {
            throw new IllegalArgumentException();
        }
        MAP.put(key, value);
    }

    @SuppressWarnings("unchecked")
    public static <T> T get(String key) {
        Object obj = MAP.get(key);
        if (obj == null) {
            throw new NullPointerException();
        }
        return (T) obj;
    }

    // -----------------------------------------------------------------------------------------------------------------

    private GlobalHolder() {
    }

}
