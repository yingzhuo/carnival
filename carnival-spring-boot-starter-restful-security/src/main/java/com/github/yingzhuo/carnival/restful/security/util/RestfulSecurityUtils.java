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
import com.github.yingzhuo.carnival.restful.security.token.Token;
import com.github.yingzhuo.carnival.restful.security.userdetails.UserDetails;
import lombok.val;

import java.util.*;

/**
 * @author 应卓
 */
@SuppressWarnings("unchecked")
public final class RestfulSecurityUtils {

    private RestfulSecurityUtils() {
        super();
    }

    public static <T extends Token> T getToken() {
        return (T) RestfulSecurityContext.getToken().orElse(null);
    }

    public static UserDetails getUserDetails() {
        return RestfulSecurityContext.getUserDetails().orElse(null);
    }

    public static <I> I getId() {
        val userDetails = getUserDetails();
        return (I) (userDetails != null ? userDetails.getId() : null);
    }

    public static String getUsername() {
        val userDetails = getUserDetails();
        return userDetails != null ? userDetails.getUsername() : null;
    }

    public static <T> T getNativeUser(Class<T> userType) {
        val userDetails = getUserDetails();
        return userDetails != null ? userDetails.getNativeUser(Objects.requireNonNull(userType)) : null;
    }

    public static List<String> getRoleNames() {
        val userDetails = getUserDetails();

        if (userDetails == null) {
            return Collections.emptyList();
        }

        return Collections.unmodifiableList(new ArrayList<>(userDetails.getRoleNames()));
    }

    public static List<String> getPermissionNames() {
        val userDetails = getUserDetails();

        if (userDetails == null) {
            return Collections.emptyList();
        }

        return Collections.unmodifiableList(new ArrayList<>(userDetails.getPermissionNames()));
    }

    public static Map<String, Object> getPayload() {
        val userDetails = getUserDetails();

        if (userDetails == null) {
            return Collections.emptyMap();
        }

        return Collections.unmodifiableMap(userDetails.getPayload());
    }

    public static <T> T getPayloadItem(String name) {
        return (T) getPayload().get(name);
    }

    public static <T> T getPayloadItemOrDefault(String name, T defaultValue) {
        final T t = getPayloadItem(name);
        return t != null ? t : defaultValue;
    }

}
