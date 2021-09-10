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

import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.core.Authentication;

import java.util.Set;
import java.util.function.Predicate;

/**
 * @author 应卓
 * @since 1.10.17
 */
public class AuthenticationTrustResolverImpl implements AuthenticationTrustResolver {

    private final Set<Predicate<Authentication>> anonymousPredicates;
    private final Set<Predicate<Authentication>> rememberMePredicates;

    public AuthenticationTrustResolverImpl(Set<Predicate<Authentication>> anonymousPredicates, Set<Predicate<Authentication>> rememberMePredicates) {
        this.anonymousPredicates = anonymousPredicates;
        this.rememberMePredicates = rememberMePredicates;
    }

    @Override
    public boolean isAnonymous(Authentication authentication) {
        if (authentication == null) {
            return false;
        }
        return anonymousPredicates.stream()
                .anyMatch(predicate -> predicate.test(authentication));
    }

    @Override
    public boolean isRememberMe(Authentication authentication) {
        if (authentication == null) {
            return false;
        }
        return rememberMePredicates.stream()
                .anyMatch(predicate -> predicate.test(authentication));
    }

}
