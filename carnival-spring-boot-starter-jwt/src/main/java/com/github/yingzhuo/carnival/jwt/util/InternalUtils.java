/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.jwt.util;

import com.auth0.jwt.algorithms.Algorithm;
import com.github.yingzhuo.carnival.jwt.SignatureAlgorithm;

import java.util.Objects;

/**
 * 内部使用工具
 *
 * @author 应卓
 */
public final class InternalUtils {

    private InternalUtils() {
    }

    public static Algorithm toAlgorithm(SignatureAlgorithm signatureAlgorithm, String secret) {
        Objects.requireNonNull(secret);
        Objects.requireNonNull(signatureAlgorithm);

        switch (signatureAlgorithm) {
            case HMAC256:
                return Algorithm.HMAC256(secret);
            case HMAC384:
                return Algorithm.HMAC384(secret);
            case HMAC512:
                return Algorithm.HMAC512(secret);
            default:
                throw new IllegalArgumentException();
        }
    }

}
