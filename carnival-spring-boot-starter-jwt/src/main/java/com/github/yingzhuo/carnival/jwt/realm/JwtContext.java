/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.jwt.realm;

import com.auth0.jwt.interfaces.DecodedJWT;

/**
 * @author 应卓
 */
@Deprecated
public final class JwtContext {

    private static final ThreadLocal<DecodedJWT> HOLDER = ThreadLocal.withInitial(() -> null);

    private JwtContext() {
        super();
    }

    public static DecodedJWT getJwt() {
        return HOLDER.get();
    }

    static void setJwt(DecodedJWT jwt) {
        HOLDER.set(jwt);
    }

}
