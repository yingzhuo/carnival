/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.exception;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 自洽性错误
 *
 * @author 应卓
 * @since 1.4.5
 */
public class SelfConsistentException extends IllegalArgumentException {

    public SelfConsistentException() {
    }

    public SelfConsistentException(String message) {
        super(message);
    }

    public SelfConsistentException(String message, Throwable cause) {
        super(message, cause);
    }

    public SelfConsistentException(Throwable cause) {
        super(cause);
    }

    public Map<String, Object> asMap() {
        return asMap(true);
    }

    public Map<String, Object> asMap(boolean includeType) {
        final Map<String, Object> map = new HashMap<>();
        map.put("message", getMessage());
        if (includeType) {
            map.put("type", getClass().getName());
        }
        return Collections.unmodifiableMap(map);
    }

}
