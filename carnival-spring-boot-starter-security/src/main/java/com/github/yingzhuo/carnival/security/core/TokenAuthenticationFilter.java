/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.security.core;

import com.github.yingzhuo.carnival.security.token.resolver.TokenResolver;
import org.springframework.security.authentication.AuthenticationProvider;

/**
 * @author 应卓
 * @see TokenAuthenticationFilterFactory
 * @since 1.10.2
 */
public class TokenAuthenticationFilter extends AbstractAuthenticationFilter {

    public TokenAuthenticationFilter(TokenResolver tokenResolver, AuthenticationProvider authenticationProvider) {
        super(tokenResolver, authenticationProvider);
    }

}
