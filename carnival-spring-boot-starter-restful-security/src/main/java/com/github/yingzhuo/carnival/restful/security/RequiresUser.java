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
import com.github.yingzhuo.carnival.restful.security.core.CheckUtils;
import com.github.yingzhuo.carnival.restful.security.exception.AuthenticationException;
import com.github.yingzhuo.carnival.restful.security.exception.RestfulSecurityException;
import com.github.yingzhuo.carnival.restful.security.userdetails.UserDetails;
import lombok.val;

import java.lang.annotation.*;

/**
 * @author 应卓
 */
@Documented
@Inherited
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Requires(RequiresUser.AuthComponent.class)
public @interface RequiresUser {

    public String username();

    public boolean caseSensitive() default true;

    public String errorMessage() default ":::<NO MESSAGE>:::";

    public static class AuthComponent implements AuthenticationComponent<RequiresUser> {

        @Override
        public void authenticate(UserDetails userDetails, RequiresUser annotation) throws RestfulSecurityException {

            val caseSensitive = annotation.caseSensitive();
            val requiredUsername = annotation.username();

            if (userDetails == null) {
                throw new AuthenticationException(CheckUtils.getMessage(annotation.errorMessage()));
            }

            if (caseSensitive) {
                if (!requiredUsername.equals(userDetails.getUsername())) {
                    throw new AuthenticationException(CheckUtils.getMessage(annotation.errorMessage()));
                }
            } else {
                if (!requiredUsername.equalsIgnoreCase(userDetails.getUsername())) {
                    throw new AuthenticationException(CheckUtils.getMessage(annotation.errorMessage()));
                }
            }
        }

    }

}
