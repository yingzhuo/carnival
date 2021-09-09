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

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.RememberMeAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.util.HashSet;
import java.util.Set;

/**
 * @author 应卓
 * @since 1.10.17
 */
public class SmartAuthenticationTrustResolver implements AuthenticationTrustResolver {

    private final Set<Class<? extends Authentication>> anonymousTypeSet = new HashSet<>();
    private final Set<Class<? extends Authentication>> rememberMeTypeSet = new HashSet<>();

    public SmartAuthenticationTrustResolver() {
        addAnonymousClass(AnonymousAuthenticationToken.class);
        addRememberMeClass(RememberMeAuthenticationToken.class);
    }

    public void addAnonymousClass(Class<? extends Authentication> anonymousClass) {
        this.anonymousTypeSet.add(anonymousClass);
    }

    public void addRememberMeClass(Class<? extends Authentication> rememberMeClass) {
        this.rememberMeTypeSet.add(rememberMeClass);
    }

    @Override
    public boolean isAnonymous(Authentication authentication) {
        if (authentication == null || anonymousTypeSet.isEmpty()) return false;
        return anonymousTypeSet.stream().anyMatch(clz -> clz.isAssignableFrom(authentication.getClass()));
    }

    @Override
    public boolean isRememberMe(Authentication authentication) {
        if (authentication == null || rememberMeTypeSet.isEmpty()) return false;
        return rememberMeTypeSet.stream().anyMatch(clz -> clz.isAssignableFrom(authentication.getClass()));
    }

    public Set<Class<? extends Authentication>> getAnonymousTypeSet() {
        return anonymousTypeSet;
    }

    public Set<Class<? extends Authentication>> getRememberMeTypeSet() {
        return rememberMeTypeSet;
    }

}
