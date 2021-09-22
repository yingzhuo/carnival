/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.security.authentication;

import com.github.yingzhuo.carnival.security.token.UsernamePasswordTokenAuthenticationToken;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

/**
 * @author 应卓
 * @since 1.10.23
 */
public abstract class BasicAuthenticationProvider implements AuthenticationProvider {

    @Override
    public final boolean supports(Class<?> authentication) {
        return UsernamePasswordTokenAuthenticationToken.class.isAssignableFrom(authentication);
    }

    @Override
    public final Authentication authenticate(Authentication authentication) throws AuthenticationException {
        return doAuthenticate((UsernamePasswordTokenAuthenticationToken) authentication);
    }

    protected abstract UsernamePasswordTokenAuthenticationToken doAuthenticate(UsernamePasswordTokenAuthenticationToken token) throws AuthenticationException;

}
