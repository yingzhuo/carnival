/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.jwt.validating.mvc;

import com.auth0.jwt.interfaces.DecodedJWT;

/**
 * @author 应卓
 */
public final class JwtContext {

    private JwtContext() {
    }

    private static final ThreadLocal<DecodedJWT> HOLDER = ThreadLocal.withInitial(() -> null);

    static void setJwt(DecodedJWT jwt) {
        HOLDER.set(jwt);
    }

    public static DecodedJWT getJwt() {
        return HOLDER.get();
    }

}
