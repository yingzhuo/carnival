/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.exception.data;

import com.github.yingzhuo.carnival.exception.CarnivalStandardException;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 应卓
 */
public class InvalidDataAccessException extends CarnivalStandardException {

    private static final long serialVersionUID = 2102462667651007747L;

    public InvalidDataAccessException() {
        this(null);
    }

    public InvalidDataAccessException(String format, Object... args) {
        this(String.format(format, args));
    }

    public InvalidDataAccessException(String message) {
        super(message);
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
