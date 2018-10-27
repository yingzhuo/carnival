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
import java.util.Objects;

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

    // ----------------------------------------------------------------------------------------------------------------

    public static final class Assert {

        private Assert() {
        }

        public static void state(boolean expression) {
            if (!expression) {
                throw new InvalidDataAccessException(null);
            }
        }

        public static void state(boolean expression, String format, Object... args) {
            if (!expression) {
                throw new InvalidDataAccessException(format, args);
            }
        }

        public static <T> T notNull(T obj) {
            state(obj != null);
            return obj;
        }

        public static <T> T notNull(T obj, String format, Object... args) {
            state(obj != null, format, args);
            return obj;
        }

        public static <T> void equals(T obj1, T obj2) {
            state(Objects.equals(obj1, obj2));
        }

        public static <T> void equals(T obj1, T obj2, String format, Object... args) {
            state(Objects.equals(obj1, obj2), format, args);
        }

        public static <T> void notEmpty(String string) {
            state(string != null && !string.isEmpty());
        }

        public static <T> void notEmpty(String string, String format, Object... args) {
            state(string != null && !string.isEmpty(), format, args);
        }

    }

}
