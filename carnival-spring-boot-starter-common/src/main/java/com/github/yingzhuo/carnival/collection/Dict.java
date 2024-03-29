/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.collection;

import java.util.HashMap;

/**
 * Another HashMap
 *
 * @author 应卓
 * @since 1.10.5
 */
@SuppressWarnings("unchecked")
public final class Dict extends HashMap<String, Object> {

    private Dict() {
    }

    public static Dict newInstance() {
        return new Dict();
    }

    public Dict add(String key, Object value) {
        super.put(key, value);
        return this;
    }

    public <T> T find(String key) {
        return (T) super.get(key);
    }

    public <T> T findOrDefault(String key, T objectIfNull) {
        return (T) super.getOrDefault(key, objectIfNull);
    }

}
