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
import com.github.yingzhuo.carnival.restful.security.exception.*;
import com.github.yingzhuo.carnival.restful.security.userdetails.UserDetails;

import java.lang.annotation.*;

import static com.github.yingzhuo.carnival.restful.security.MessageUtils.getMessage;

/**
 * @author 应卓
 */
@Documented
@Inherited
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Requires(RequiresNotRoot.AuthComponent.class)
public @interface RequiresNotRoot {

    public String errorMessage() default ":::<NO MESSAGE>:::";

    public static class AuthComponent implements AuthenticationComponent<RequiresNotRoot> {
        @Override
        public void authenticate(UserDetails userDetails, RequiresNotRoot annotation) throws RestfulSecurityException {

            if (userDetails == null) {
                throw new AuthenticationException(getMessage(annotation.errorMessage()));
            }

            if (userDetails.isExpired()) {
                throw new UserDetailsExpiredException(getMessage(annotation.errorMessage()));
            }

            if (userDetails.isLocked()) {
                throw new UserDetailsLockedException(getMessage(annotation.errorMessage()));
            }

            if (userDetails.isRoot()) {
                throw new AuthorizationException(getMessage(annotation.errorMessage()));
            }
        }
    }

}
