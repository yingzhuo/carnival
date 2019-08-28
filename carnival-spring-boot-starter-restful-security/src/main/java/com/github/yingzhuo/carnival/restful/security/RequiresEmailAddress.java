/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.restful.security;

import com.github.yingzhuo.carnival.restful.security.annotation.AuthenticationComponent;
import com.github.yingzhuo.carnival.restful.security.annotation.Requires;
import com.github.yingzhuo.carnival.restful.security.exception.AuthenticationException;
import com.github.yingzhuo.carnival.restful.security.exception.RestfulSecurityException;
import com.github.yingzhuo.carnival.restful.security.exception.UserDetailsExpiredException;
import com.github.yingzhuo.carnival.restful.security.exception.UserDetailsLockedException;
import com.github.yingzhuo.carnival.restful.security.userdetails.UserDetails;
import lombok.val;
import org.apache.commons.lang3.StringUtils;

import java.lang.annotation.*;

import static com.github.yingzhuo.carnival.restful.security.MessageUtils.getMessage;

/**
 * @author 应卓
 * @since 1.1.5
 */
@Documented
@Inherited
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Requires(RequiresUsername.AuthComponent.class)
public @interface RequiresEmailAddress {

    public String value();

    public String errorMessage() default ":::<NO MESSAGE>:::";

    public static class AuthComponent implements AuthenticationComponent<RequiresEmailAddress> {
        @Override
        public void authenticate(UserDetails userDetails, RequiresEmailAddress annotation) throws RestfulSecurityException {

            if (userDetails == null) {
                throw new AuthenticationException(getMessage(annotation.errorMessage()));
            }

            if (userDetails.isLocked()) {
                throw new UserDetailsLockedException(getMessage(annotation.errorMessage()));
            }

            if (userDetails.isExpired()) {
                throw new UserDetailsExpiredException(getMessage(annotation.errorMessage()));
            }

            if (userDetails.getEmailAddress() == null) {
                throw new AuthenticationException(getMessage(annotation.errorMessage()));
            }

            val required = annotation.value();
            val actual = userDetails.getEmailAddress();
            if (!StringUtils.equalsIgnoreCase(required, actual)) {
                throw new AuthenticationException(getMessage(annotation.errorMessage()));
            }
        }
    }

}
