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

import com.github.yingzhuo.carnival.common.datamodel.Gender;
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
    }

    // -----------------------------------------------------------------------------------------------------------------

    public static UserDetails get() {
        return RestfulSecurityContext.getUserDetails().orElse(null);
    }

    public static <I> I getId() {
        val userDetails = get();
        return (I) (userDetails != null ? userDetails.getId() : null);
    }

    public static String getUsername() {
        val userDetails = get();
        return userDetails != null ? userDetails.getUsername() : null;
    }

    public static String getEmail() {
        val userDetails = get();
        return userDetails != null ? userDetails.getEmail() : null;
    }

    public static String getPassword() {
        val userDetails = get();
        return userDetails != null ? userDetails.getPassword() : null;
    }

    public static Date getDateOfBirth() {
        val userDetails = get();
        return userDetails != null ? userDetails.getDateOfBirth() : null;
    }

    public static boolean isAdmin() {
        val userDetails = get();
        return userDetails != null && userDetails.isAdmin();
    }

    public static boolean isNotAdmin() {
        val userDetails = get();
        return userDetails != null && userDetails.isNotAdmin();
    }

    public static Gender getGender() {
        val userDetails = get();
        return userDetails != null ? userDetails.getGender() : null;
    }

    public static <T> T getNativeUser() {
        val userDetails = get();
        return (T) (userDetails != null ? userDetails.getNativeUser() : null);
    }

    public static List<String> getRoleNames() {
        val userDetails = get();
        if (userDetails == null) {
            return Collections.emptyList();
        }
        return Collections.unmodifiableList(new ArrayList<>(userDetails.getRoleNames()));
    }

    public static Set<String> getRoleNamesAsSet() {
        val userDetails = get();
        if (userDetails == null) {
            return Collections.emptySet();
        }
        return Collections.unmodifiableSet(new HashSet<>(userDetails.getRoleNames()));
    }

    public static List<String> getPermissionNames() {
        val userDetails = get();
        if (userDetails == null) {
            return Collections.emptyList();
        }
        return Collections.unmodifiableList(new ArrayList<>(userDetails.getPermissionNames()));
    }

    public static Set<String> getPermissionNamesAsSet() {
        val userDetails = get();
        if (userDetails == null) {
            return Collections.emptySet();
        }
        return Collections.unmodifiableSet(new HashSet<>(userDetails.getPermissionNames()));
    }

    public static Map<String, Object> getPayload() {
        val userDetails = get();
        if (userDetails == null) {
            return Collections.emptyMap();
        }
        return Collections.unmodifiableMap(userDetails.getPayload());
    }

    public static int getPayloadSize() {
        val userDetails = get();
        return userDetails != null ? userDetails.getPayloadSize() : 0;
    }

    public static <T> T getPayloadItem(String name) {
        return (T) getPayload().get(name);
    }

    public static <T> T getPayloadItem(String name, T defaultValue) {
        final T t = getPayloadItem(name);
        return t != null ? t : defaultValue;
    }

    public static <T> T getProperty(String propertyName) {
        return getProperty(propertyName, null);
    }

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
