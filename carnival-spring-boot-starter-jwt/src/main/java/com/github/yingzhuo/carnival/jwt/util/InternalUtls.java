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

import java.io.UnsupportedEncodingException;
import java.util.Objects;

public final class InternalUtls {

    private InternalUtls() {
    }

    public static Algorithm toAlgorithm(SignatureAlgorithm signatureAlgorithm, String s) {
        Objects.requireNonNull(signatureAlgorithm);
        Objects.requireNonNull(s);

        try {
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
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException(); // 不可能抛出此异常
        }
    }

}
