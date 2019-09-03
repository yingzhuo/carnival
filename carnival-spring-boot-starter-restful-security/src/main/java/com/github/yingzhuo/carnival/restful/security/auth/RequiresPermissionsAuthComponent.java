/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.restful.security.auth;

import com.github.yingzhuo.carnival.restful.security.Logical;
import com.github.yingzhuo.carnival.restful.security.RequiresPermissions;
import com.github.yingzhuo.carnival.restful.security.annotation.AuthenticationComponent;
import com.github.yingzhuo.carnival.restful.security.exception.*;
import com.github.yingzhuo.carnival.restful.security.token.Token;
import com.github.yingzhuo.carnival.restful.security.userdetails.UserDetails;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.github.yingzhuo.carnival.restful.security.auth.MessageUtils.getMessage;

/**
 * @author 应卓
 * @since 1.1.5
 */
public class RequiresPermissionsAuthComponent implements AuthenticationComponent<RequiresPermissions> {

    @Override
    public void authenticate(Token token, UserDetails userDetails, RequiresPermissions annotation) throws RestfulSecurityException {
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
        Set<String> actual = new HashSet<>(userDetails.getPermissionNames());
        if (annotation.logical() == Logical.ALL) {
            if (!actual.containsAll(require)) {
                throw new AuthorizationException(getMessage(annotation.errorMessage()));
            }
        } else {
            if (require.stream().noneMatch(actual::contains)) {
                throw new AuthorizationException(getMessage(annotation.errorMessage()));
            }
        }
    }

}
