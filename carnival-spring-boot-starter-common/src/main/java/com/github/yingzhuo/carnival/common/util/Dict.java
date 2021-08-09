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

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 应卓
 * @since 1.10.5
 */
@SuppressWarnings("unchecked")
public class Dict extends HashMap<String, Object> implements Map<String, Object> {

    public static Dict newInstance() {
        return new Dict();
    }

    private Dict() {
    }

    public Dict put(String key, Object value) {
        super.put(key, value);
        return this;
    }

    public <T> T find(String key) {
        return (T) super.get(key);
    }

    public <T> T findOrDefault(String key, T objectIfNull) {
        return (T) super.getOrDefault(key, objectIfNull);
    }

    public static class KV implements Serializable {
        private final String k;
        private final Object v;

        public KV(String k, Object v) {
            this.k = k;
            this.v = v;
        }

        public String k() {
            return this.k;
        }

        public Object v() {
            return this.v;
        }
    }

}
