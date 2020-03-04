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

import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

/**
 * 逻辑自洽性
 *
 * @author 应卓
 */
public final class SelfConsistent {

    public static <T> T notNull(T obj) {
        return notNull(obj, null);
    }

    public static <T> T notNull(T obj, String message) {
        if (obj == null) {
            throw new SelfConsistentException(message);
        }
        return obj;
    }

    public static <T> void equals(T obj1, T obj2) {
        equals(obj1, obj2, null);
    }

    public static <T> void equals(T obj1, T obj2, String message) {
        if (!Objects.equals(obj1, obj2)) {
            throw new SelfConsistentException(message);
        }
    }

    public static <T> void notEquals(T obj1, T obj2) {
        notEquals(obj1, obj2, null);
    }

    public static <T> void notEquals(T obj1, T obj2, String message) {
        if (Objects.equals(obj1, obj2)) {
            throw new SelfConsistentException(message);
        }
    }

    public static String notEmpty(String string) {
        return notEmpty(string, null);
    }

    public static String notEmpty(String string, String message) {
        if (StringUtils.isEmpty(string)) {
            throw new SelfConsistentException(message);
        }
        return string;
    }

    public static String notBlank(String string) {
        return notBlank(string, null);
    }

    public static String notBlank(String string, String message) {
        if (StringUtils.isBlank(string)) {
            throw new SelfConsistentException(message);
        }
        return string;
    }

    // -----------------------------------------------------------------------------------------------------------------

    private SelfConsistent() {
    }

}
