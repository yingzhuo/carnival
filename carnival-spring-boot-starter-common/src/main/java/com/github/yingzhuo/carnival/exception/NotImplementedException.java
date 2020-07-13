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
 * @author 应卓
 * @since 1.6.25
 */
public class NotImplementedException extends UnsupportedOperationException {

    public NotImplementedException() {
        super();
    }

    public Map<String, Object> asMap() {
        return asMap(true);
    }

    public Map<String, Object> asMap(boolean includeType) {
        final Map<String, Object> map = new HashMap<>();
        map.put("message", getMessage());
        if (includeType) {
            map.put("type", NotImplementedException.class.getName());
        }
        return Collections.unmodifiableMap(map);
    }

}
