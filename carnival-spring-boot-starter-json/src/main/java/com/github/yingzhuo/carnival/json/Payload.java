/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.json;

import com.fasterxml.jackson.annotation.JsonView;

import java.util.HashMap;

/**
 * @author 应卓
 */
@JsonView(Views.Normal.class)
public class Payload extends HashMap<Object, Object> {

    public static Payload empty() {
        return new Payload();
    }

    public static Payload of(String k, Object v) {
        final Payload payload = new Payload();
        payload.put(k, v);
        return payload;
    }

    public static Payload of(
            String k1, Object v1,
            String k2, Object v2
    ) {
        final Payload payload = new Payload();
        payload.put(k1, v1);
        payload.put(k2, v2);
        return payload;
    }

    public static Payload of(
            String k1, Object v1,
            String k2, Object v2,
            String k3, Object v3
    ) {
        final Payload payload = new Payload();
        payload.put(k1, v1);
        payload.put(k2, v2);
        payload.put(k3, v3);
        return payload;
    }

    public static Payload of(
            String k1, Object v1,
            String k2, Object v2,
            String k3, Object v3,
            String k4, Object v4
    ) {
        final Payload payload = new Payload();
        payload.put(k1, v1);
        payload.put(k2, v2);
        payload.put(k3, v3);
        payload.put(k4, v4);
        return payload;
    }

    public static Payload of(
            String k1, Object v1,
            String k2, Object v2,
            String k3, Object v3,
            String k4, Object v4,
            String k5, Object v5
    ) {
        final Payload payload = new Payload();
        payload.put(k1, v1);
        payload.put(k2, v2);
        payload.put(k3, v3);
        payload.put(k4, v4);
        payload.put(k5, v5);
        return payload;
    }

    public Payload append(String key, Object value) {
        this.put(key, value);
        return this;
    }

}
