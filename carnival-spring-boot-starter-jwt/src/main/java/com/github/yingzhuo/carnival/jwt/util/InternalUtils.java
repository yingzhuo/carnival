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

    public static Algorithm toAlgorithm(SignatureAlgorithm signatureAlgorithm, String s) {
        Objects.requireNonNull(signatureAlgorithm);
        Objects.requireNonNull(s);

        switch (signatureAlgorithm) {
            case HMAC256:
                return Algorithm.HMAC256(s);
            case HMAC384:
                return Algorithm.HMAC384(s);
            case HMAC512:
                return Algorithm.HMAC512(s);
            default:
                throw new IllegalArgumentException();
        }
    }

}
