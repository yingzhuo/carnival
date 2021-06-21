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

import java.util.Collection;

/**
 * @author 应卓
 * @since 1.9.6
 */
public final class Setter {

    private Setter() {
    }

    public static <T> T setProp(T obj, String property, Object value) {
        if (obj == null) return null;
        BeanWrapper w = new BeanWrapperImpl(obj);
        w.setPropertyValue(property, value);
        return obj;
    }

    public static <T> T setPropQuietly(T obj, String property, Object value) {
        try {
            return setProp(obj, property, value);
        } catch (Exception e) {
            return obj;
        }
    }

    public static <T> void setProp(Collection<T> objs, String property, Object value) {
        for (Object obj : objs) {
            setProp(obj, property, value);
        }
    }

    public static <T> void setPropQuietly(Collection<T> objs, String property, Object value) {
        for (Object obj : objs) {
            setPropQuietly(obj, property, value);
        }
    }

    public static <T> void setProp(T[] array, String property, Object value) {
        for (Object obj : array) {
            setProp(obj, property, value);
        }
    }

    public static <T> void setPropQuietly(T[] array, String property, Object value) {
        for (Object obj : array) {
            setPropQuietly(obj, property, value);
        }
    }

}