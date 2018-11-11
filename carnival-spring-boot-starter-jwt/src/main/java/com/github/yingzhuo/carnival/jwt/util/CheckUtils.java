/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.jwt.util;

import com.github.yingzhuo.carnival.jwt.RequiresJwt;
import com.github.yingzhuo.carnival.restful.security.core.RestfulSecurityContext;
import com.github.yingzhuo.carnival.restful.security.exception.AuthenticationException;
import com.github.yingzhuo.carnival.restful.security.exception.UserDetailsExpiredException;
import com.github.yingzhuo.carnival.restful.security.exception.UserDetailsLockedException;
import com.github.yingzhuo.carnival.restful.security.userdetails.UserDetails;
import org.springframework.util.StringUtils;

import java.util.Optional;

/**
 * @author 应卓
 */
final public class CheckUtils {

    private CheckUtils() {
    }

    private static String getMessage(String message) {
        if (!StringUtils.hasText(message) || ":::<NO MESSAGE>:::".equals(message)) {
            return null;
        }
        return message;
    }

    public static void check(RequiresJwt annotation) {
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

}
