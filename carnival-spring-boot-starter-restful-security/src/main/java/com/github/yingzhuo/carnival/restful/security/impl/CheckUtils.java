/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.restful.security.impl;

import com.github.yingzhuo.carnival.restful.security.*;
import com.github.yingzhuo.carnival.restful.security.autoconfig.UserDetailsExpiredException;
import com.github.yingzhuo.carnival.restful.security.autoconfig.UserDetailsLockedException;
import com.github.yingzhuo.carnival.restful.security.exception.AuthenticationException;
import com.github.yingzhuo.carnival.restful.security.exception.AuthorizationException;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * 元注释检查工具 (内部工具)
 *
 * @author 应卓
 */
public class CheckUtils {

    private CheckUtils() {
    }

    private static String getMessage(String message) {
        if (!StringUtils.hasText(message) || ":::<NO MESSAGE>:::".equals(message)) {
            return null;
        }
        return message;
    }

    public static void check(RequiresAuthentication annotation) {
        if (annotation == null) return;

        Optional<UserDetails> userDetailsOptional = RestfulSecurityContext.getUserDetails();
        if (!userDetailsOptional.isPresent()) {
            throw new AuthenticationException(getMessage(annotation.errorMessage()));
        }

        UserDetails userDetails = userDetailsOptional.get();

        if (userDetails.isExpired()) {
            throw new UserDetailsExpiredException(getMessage(annotation.errorMessage()));
        }

        if (userDetails.isLocked()) {
            throw new UserDetailsLockedException(getMessage(annotation.errorMessage()));
        }
    }

    public static void check(RequiresGuest annotation) {
        if (annotation == null) return;

        Optional<UserDetails> userDetailsOptional = RestfulSecurityContext.getUserDetails();
        if (userDetailsOptional.isPresent()) {
            throw new AuthenticationException(getMessage(annotation.errorMessage()));
        }
    }

    public static void check(RequiresRoles annotation) {
        if (annotation == null) return;

        Optional<UserDetails> userDetailsOptional = RestfulSecurityContext.getUserDetails();
        if (!userDetailsOptional.isPresent()) {
            throw new AuthenticationException(getMessage(annotation.errorMessage()));
        }

        UserDetails userDetails = userDetailsOptional.get();

        if (userDetails.isExpired()) {
            throw new UserDetailsExpiredException(getMessage(annotation.errorMessage()));
        }

        if (userDetails.isLocked()) {
            throw new UserDetailsLockedException(getMessage(annotation.errorMessage()));
        }

        List<String> reqired = Arrays.asList(annotation.value());
        Set<String> actual = new HashSet<>(userDetails.getRoleNames());
        if (annotation.logical() == Logical.AND) {
            if (!reqired.stream().allMatch(actual::contains)) {
                throw new AuthorizationException(getMessage(annotation.errorMessage()));
            }
        } else {
            if (reqired.stream().noneMatch(actual::contains)) {
                throw new AuthorizationException(getMessage(annotation.errorMessage()));
            }
        }
    }

    public static void check(RequiresPermissions annotation) {
        if (annotation == null) return;

        Optional<UserDetails> userDetailsOptional = RestfulSecurityContext.getUserDetails();
        if (!userDetailsOptional.isPresent()) {
            throw new AuthenticationException(getMessage(annotation.errorMessage()));
        }

        UserDetails userDetails = userDetailsOptional.get();

        if (userDetails.isExpired()) {
            throw new UserDetailsExpiredException(getMessage(annotation.errorMessage()));
        }

        if (userDetails.isLocked()) {
            throw new UserDetailsLockedException(getMessage(annotation.errorMessage()));
        }

        List<String> reqired = Arrays.asList(annotation.value());
        Set<String> actual = new HashSet<>(userDetails.getRoleNames());
        if (annotation.logical() == Logical.AND) {
            if (!reqired.stream().allMatch(actual::contains)) {
                throw new AuthorizationException(getMessage(annotation.errorMessage()));
            }
        } else {
            if (reqired.stream().noneMatch(actual::contains)) {
                throw new AuthorizationException(getMessage(annotation.errorMessage()));
            }
        }
    }
}
