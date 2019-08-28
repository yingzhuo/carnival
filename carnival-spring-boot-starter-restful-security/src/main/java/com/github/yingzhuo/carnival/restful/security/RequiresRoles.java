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

import java.lang.annotation.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.github.yingzhuo.carnival.restful.security.MessageUtils.getMessage;

/**
 * @author 应卓
 */
@Documented
@Inherited
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Requires(RequiresRoles.AuthComponent.class)
public @interface RequiresRoles {

    public String[] value();

    public Logical logical() default Logical.OR;

    public String errorMessage() default ":::<NO MESSAGE>:::";

    public static class AuthComponent implements AuthenticationComponent<RequiresRoles> {

        @Override
        public void authenticate(Token token, UserDetails userDetails, RequiresRoles annotation) throws RestfulSecurityException {
            if (userDetails == null) {
                throw new AuthenticationException(getMessage(annotation.errorMessage()));
            }

            if (userDetails.isLocked()) {
                throw new UserDetailsLockedException(getMessage(annotation.errorMessage()));
            }

            if (userDetails.isExpired()) {
                throw new UserDetailsExpiredException(getMessage(annotation.errorMessage()));
            }

            List<String> require = Arrays.asList(annotation.value());
            Set<String> actual = new HashSet<>(userDetails.getRoleNames());

            if (annotation.logical() == Logical.OR) {
                if (require.stream().noneMatch(actual::contains)) {
                    throw new AuthorizationException(getMessage(annotation.errorMessage()));
                }
            } else {
                if (!actual.containsAll(require)) {
                    throw new AuthorizationException(getMessage(annotation.errorMessage()));
                }
            }
        }
    }

}
