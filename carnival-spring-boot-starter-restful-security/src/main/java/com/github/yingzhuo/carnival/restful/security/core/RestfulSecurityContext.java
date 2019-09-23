/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.restful.security.core;

import com.github.yingzhuo.carnival.restful.security.token.Token;
import com.github.yingzhuo.carnival.restful.security.userdetails.UserDetails;

import java.util.Optional;

/**
 * 安全上下文
 *
 * @author 应卓
 */
public final class RestfulSecurityContext {

    private final static ThreadLocal<Token> tokenHolder = ThreadLocal.withInitial(() -> null);
    private final static ThreadLocal<UserDetails> userDetailsHolder = ThreadLocal.withInitial(() -> null);
    private final static ThreadLocal<Boolean> ignored = ThreadLocal.withInitial(() -> Boolean.FALSE);

    private RestfulSecurityContext() {
    }

    public static Optional<Token> getToken() {
        return Optional.ofNullable(tokenHolder.get());
    }

    public static Optional<UserDetails> getUserDetails() {
        return Optional.ofNullable(userDetailsHolder.get());
    }

    public static boolean getIgnored() {
        return ignored.get();
    }

    // -----------------------------------------------------------------------------------------------------------------

    static void setUserDetails(UserDetails userDetails) {
        userDetailsHolder.set(userDetails);
    }

    static void setToken(Token token) {
        tokenHolder.set(token);
    }

    static void setIgnored(boolean ignore) {
        ignored.set(ignore);
    }
    static void clean() {
        tokenHolder.set(null);
        userDetailsHolder.set(null);
        ignored.set(false);
    }

}
