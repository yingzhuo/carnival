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
import com.github.yingzhuo.carnival.restful.security.token.Token;
import com.github.yingzhuo.carnival.restful.security.userdetails.UserDetails;
import lombok.val;

import java.lang.annotation.*;
import java.util.Objects;

import static com.github.yingzhuo.carnival.restful.security.MessageUtils.getMessage;

/**
 * @author 应卓
 */
@Documented
@Inherited
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Requires(RequiresId.AuthComponent.class)
public @interface RequiresId {

    public String value();

    public String errorMessage() default ":::<NO MESSAGE>:::";

    public static class AuthComponent implements AuthenticationComponent<RequiresId> {

        @Override
        public void authenticate(Token token, UserDetails userDetails, RequiresId annotation) throws RestfulSecurityException {
            val expect = annotation.value();

            if (userDetails == null) {
                throw new AuthenticationException(getMessage(annotation.errorMessage()));
            }

            if (userDetails.isLocked()) {
                throw new UserDetailsLockedException(getMessage(annotation.errorMessage()));
            }

            if (userDetails.isExpired()) {
                throw new UserDetailsExpiredException(getMessage(annotation.errorMessage()));
            }

            if (userDetails.getId() == null || !Objects.equals(expect, userDetails.getId())) {
                throw new AuthorizationException(getMessage(annotation.errorMessage()));
            }
        }
    }

}
