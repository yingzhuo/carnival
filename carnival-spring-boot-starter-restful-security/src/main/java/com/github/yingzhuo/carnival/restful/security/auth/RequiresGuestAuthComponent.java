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

import com.github.yingzhuo.carnival.restful.security.RequiresGuest;
import com.github.yingzhuo.carnival.restful.security.annotation.AuthenticationComponent;
import com.github.yingzhuo.carnival.restful.security.exception.AuthenticationException;
import com.github.yingzhuo.carnival.restful.security.exception.RestfulSecurityException;
import com.github.yingzhuo.carnival.restful.security.token.Token;
import com.github.yingzhuo.carnival.restful.security.userdetails.UserDetails;

import static com.github.yingzhuo.carnival.restful.security.auth.MessageUtils.getMessage;

/**
 * @author 应卓
 * @since 1.1.5
 */
public class RequiresGuestAuthComponent implements AuthenticationComponent<RequiresGuest> {

    @Override
    public void authenticate(Token token, UserDetails userDetails, RequiresGuest annotation) throws RestfulSecurityException {
        if (userDetails != null) {
            throw new AuthenticationException(getMessage(annotation.errorMessage()));
        }
    }

}
