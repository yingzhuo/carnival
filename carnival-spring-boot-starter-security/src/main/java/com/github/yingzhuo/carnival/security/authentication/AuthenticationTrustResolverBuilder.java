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
import java.util.function.Predicate;

/**
 * @author 应卓
 * @see org.springframework.security.authentication.AuthenticationTrustResolver
 * @see org.springframework.security.authentication.AuthenticationTrustResolverImpl
 * @see com.github.yingzhuo.carnival.security.authentication.AuthenticationTrustResolverImpl
 * @see RememberMeAuthenticationToken
 * @see AnonymousAuthenticationToken
 * @see com.github.yingzhuo.carnival.security.token.MutableToken
 * @since 1.10.17
 */
public final class AuthenticationTrustResolverBuilder {

    private final Set<Predicate<Authentication>> anonymousPredicates = new HashSet<>();
    private final Set<Predicate<Authentication>> rememberMePredicates = new HashSet<>();

    private AuthenticationTrustResolverBuilder() {
    }

    public static AuthenticationTrustResolverBuilder newInstance() {
        return new AuthenticationTrustResolverBuilder();
    }

    public static AuthenticationTrustResolver newDefault() {
        return newInstance()
                .addAnonymousAuthenticationType(AnonymousAuthenticationToken.class)
                .addRememberMeAuthenticationType(RememberMeAuthenticationToken.class)
                .build();
    }

    public AuthenticationTrustResolverBuilder addAnonymousAuthenticationType(Class<? extends Authentication> clazz) {
        return addAnonymousPredicate(ByType.of(clazz));
    }

    public AuthenticationTrustResolverBuilder addRememberMeAuthenticationType(Class<? extends Authentication> clazz) {
        return addRememberMePredicate(ByType.of(clazz));
    }

    public AuthenticationTrustResolverBuilder addAnonymousPredicate(Predicate<Authentication> predicate) {
        this.anonymousPredicates.add(predicate);
        return this;
    }

    public AuthenticationTrustResolverBuilder addRememberMePredicate(Predicate<Authentication> predicate) {
        this.rememberMePredicates.add(predicate);
        return this;
    }

    public AuthenticationTrustResolver build() {
        return new AuthenticationTrustResolverImpl(
                anonymousPredicates,
                rememberMePredicates
        );
    }

    // ---------------------------------------------------------------------------------------------------------------

    public static class ByType implements Predicate<Authentication> {

        private final Class<? extends Authentication> clz;

        private ByType(Class<? extends Authentication> clz) {
            this.clz = clz;
        }

        public static ByType of(Class<? extends Authentication> clz) {
            return new ByType(clz);
        }

        @Override
        public boolean test(Authentication authentication) {
            if (clz == null || authentication == null) {
                return false;
            }
            return clz.isAssignableFrom(authentication.getClass());
        }
    }

}
