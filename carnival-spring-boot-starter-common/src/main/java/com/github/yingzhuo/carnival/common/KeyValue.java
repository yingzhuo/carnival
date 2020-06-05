/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.common;

import java.util.Map;

/**
 * @param <K> key type
 * @param <V> value type
 * @author 应卓
 */
public final class KeyValue<K, V> implements Map.Entry<K, V> {

    private final K k;
    private final V v;

    private KeyValue(K k, V v) {
        this.k = k;
        this.v = v;
    }

    public static <K, V> KeyValue<K, V> of(K key, V value) {
        return new KeyValue<>(key, value);
    }

    @Override
    public K getKey() {
        return k;
    }

    @Override
    public V getValue() {
        return v;
    }

    @Override
    public V setValue(V value) {
        throw new UnsupportedOperationException();
    }

}
