/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.restful.security.util;

import com.github.yingzhuo.carnival.restful.security.core.RestfulSecurityContext;
import com.github.yingzhuo.carnival.restful.security.userdetails.UserDetails;
import lombok.val;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.BeansException;

import java.util.*;

/**
 * @author 应卓
 */
@SuppressWarnings("unchecked")
public final class UserDetailsUtils {

    private UserDetailsUtils() {
        super();
    }

    /**
     * 获取令牌UserDetails实例
     */
    public static UserDetails get() {
        return RestfulSecurityContext.getUserDetails().orElse(null);
    }

    /**
     * 获取ID实例
     *
     * @param <I> ID类型
     */
    public static <I> I getId() {
        val userDetails = get();
        return (I) (userDetails != null ? userDetails.getId() : null);
    }

    /**
     * 获取用户名
     */
    public static String getUsername() {
        val userDetails = get();
        return userDetails != null ? userDetails.getUsername() : null;
    }

    /**
     * 获取口令
     */
    public static String getPassword() {
        val userDetails = get();
        return userDetails != null ? userDetails.getPassword() : null;
    }

    /**
     * 获取本地User对象
     *
     * @param <T> 对象类型
     */
    public static <T> T getNativeUser() {
        val userDetails = get();
        return (T) (userDetails != null ? userDetails.getNativeUser() : null);
    }

    /**
     * 获取角色名称
     */
    public static List<String> getRoleNames() {
        val userDetails = get();

        if (userDetails == null) {
            return Collections.emptyList();
        }

        return Collections.unmodifiableList(new ArrayList<>(userDetails.getRoleNames()));
    }

    /**
     * 获取角色名称
     */
    public static Set<String> getRoleNamesAsSet() {
        val userDetails = get();

        if (userDetails == null) {
            return Collections.emptySet();
        }

        return Collections.unmodifiableSet(new HashSet<>(userDetails.getRoleNames()));
    }

    /**
     * 获取权限名称
     */
    public static List<String> getPermissionNames() {
        val userDetails = get();

        if (userDetails == null) {
            return Collections.emptyList();
        }

        return Collections.unmodifiableList(new ArrayList<>(userDetails.getPermissionNames()));
    }

    /**
     * 获取权限名称
     */
    public static Set<String> getPermissionNamesAsSet() {
        val userDetails = get();

        if (userDetails == null) {
            return Collections.emptySet();
        }

        return Collections.unmodifiableSet(new HashSet<>(userDetails.getPermissionNames()));
    }

    /**
     * 获取用户负载
     */
    public static Map<String, Object> getPayload() {
        val userDetails = get();

        if (userDetails == null) {
            return Collections.emptyMap();
        }

        return Collections.unmodifiableMap(userDetails.getPayload());
    }

    /**
     * 获取负载尺寸
     */
    public static int getPayloadSize() {
        val userDetails = get();
        return userDetails != null ? userDetails.getPayloadSize() : 0;
    }

    /**
     * 获取负载中的数据
     *
     * @param name 名称
     * @param <T>  数据类型
     * @return 数据
     */
    public static <T> T getPayloadItem(String name) {
        return (T) getPayload().get(name);
    }

    /**
     * 获取负载中的数据
     *
     * @param name         名称
     * @param <T>          数据类型
     * @param defaultValue 默认值
     * @return 数据
     */
    public static <T> T getPayloadItem(String name, T defaultValue) {
        final T t = getPayloadItem(name);
        return t != null ? t : defaultValue;
    }

    /**
     * 通过表达式获取UserDetails中的对象
     *
     * @param propertyName 表达式
     * @param <T>          数据类型
     * @return 数据
     */
    public static <T> T getProperty(String propertyName) {
        return getProperty(propertyName, null);
    }

    /**
     * 通过表达式获取UserDetails中的对象
     *
     * @param propertyName 表达式
     * @param defaultValue 默认值
     * @param <T>          数据类型
     * @return 数据
     */
    public static <T> T getProperty(String propertyName, T defaultValue) {
        final UserDetails userDetails = get();

        if (userDetails == null) {
            return defaultValue;
        }

        try {
            T result = (T) new BeanWrapperImpl(userDetails).getPropertyValue(propertyName);
            return result != null ? result : defaultValue;
        } catch (BeansException e) {
            return defaultValue;
        }
    }

}
