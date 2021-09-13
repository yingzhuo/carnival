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

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * @author 应卓
 * @since 1.10.19
 */
public class CompositeAuthenticationProvider implements AuthenticationProvider {

    private final List<AuthenticationProvider> providers;

    public CompositeAuthenticationProvider(List<AuthenticationProvider> providers) {
        this.providers = Optional.ofNullable(providers).orElse(Collections.emptyList());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return providers.stream().anyMatch(provider -> provider.supports(authentication));
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        return providers.stream().filter(provider -> provider.supports(authentication.getClass())).findFirst().map(provider -> provider.authenticate(authentication)).orElse(null);
    }

}
