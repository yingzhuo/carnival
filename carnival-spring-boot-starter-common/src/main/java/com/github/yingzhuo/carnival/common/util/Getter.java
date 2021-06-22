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

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.util.Optional;

/**
 * @author 应卓
 * @since 1.9.6
 */
public final class Getter {

    private Getter() {
    }

    public static <T> T getProp(Object obj, String property) {
        if (obj == null) return null;
        BeanWrapper w = new BeanWrapperImpl(obj);
        return (T) w.getPropertyValue(property);
    }

    public static <T> T getPropQuietly(Object obj, String property) {
        try {
            return getProp(obj, property);
        } catch (Exception e) {
            return null;
        }
    }

    public static <T> T getPropQuietly(Object obj, String property, T defaultIfNull) {
        return (T) Optional.ofNullable(getPropQuietly(obj, property)).orElse(defaultIfNull);
    }

}
