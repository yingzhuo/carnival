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

import com.github.yingzhuo.carnival.restful.security.RequiresAuthentication;
import com.github.yingzhuo.carnival.restful.security.annotation.AuthenticationComponent;
import com.github.yingzhuo.carnival.restful.security.core.RestfulSecurityContext;
import com.github.yingzhuo.carnival.restful.security.exception.AuthenticationException;
import com.github.yingzhuo.carnival.restful.security.exception.RestfulSecurityException;
import com.github.yingzhuo.carnival.restful.security.exception.UserDetailsExpiredException;
import com.github.yingzhuo.carnival.restful.security.exception.UserDetailsLockedException;
import com.github.yingzhuo.carnival.restful.security.token.Token;
import com.github.yingzhuo.carnival.restful.security.userdetails.UserDetails;

/**
 * @author 应卓
 * @since 1.1.5
 */
public class RequiresAuthenticationAuthComponent implements AuthenticationComponent<RequiresAuthentication> {

    @Override
    public void authenticate(Token token, UserDetails userDetails, RequiresAuthentication annotation) throws RestfulSecurityException {
        if (userDetails == null) {
            throw new AuthenticationException(RestfulSecurityContext.current().getRequest());
        }

        if (userDetails.isLocked()) {
            throw new UserDetailsLockedException(RestfulSecurityContext.current().getRequest());
        }

        if (userDetails.isExpired()) {
            throw new UserDetailsExpiredException(RestfulSecurityContext.current().getRequest());
        }
    }

}
