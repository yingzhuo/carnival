/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.restful.security;

import java.util.Optional;

/**
 * 安全上下文
 *
 * @author 应卓
 */
public final class RestfulSecurityContext {

    private static ThreadLocal<Token> tokenHolder = ThreadLocal.withInitial(() -> null);
    private static ThreadLocal<UserDetails> userDetailsHolder = ThreadLocal.withInitial(() -> null);

    /**
     * 私有构造
     */
    private RestfulSecurityContext() {
    }

    public static void setToken(Token token) {
        if (token != null) {
            tokenHolder.set(token);
        }
    }

    public static void setUserDetails(UserDetails userDetails) {
        if (userDetails != null) {
            userDetailsHolder.set(userDetails);
        }
    }

    public static Optional<Token> getToken() {
        return Optional.ofNullable(tokenHolder.get());
    }

    public static Optional<UserDetails> getUserDetails() {
        return Optional.ofNullable(userDetailsHolder.get());
    }

}
