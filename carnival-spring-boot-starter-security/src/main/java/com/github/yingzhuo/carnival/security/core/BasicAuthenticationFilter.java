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

import com.github.yingzhuo.carnival.security.authentication.BasicAuthenticationProvider;
import com.github.yingzhuo.carnival.security.token.resolver.BasicTokenResolver;
import org.springframework.security.authentication.AuthenticationProvider;

/**
 * @author 应卓
 * @see com.github.yingzhuo.carnival.security.authentication.BasicAuthenticationProvider
 * @since 1.10.22
 */
public class BasicAuthenticationFilter extends AbstractAuthenticationFilter {

    public BasicAuthenticationFilter(BasicAuthenticationProvider authenticationProvider) {
        this((AuthenticationProvider) authenticationProvider);
    }

    public BasicAuthenticationFilter(AuthenticationProvider authenticationProvider) {
        super(BasicTokenResolver.INSTANCE, authenticationProvider);
    }

}
