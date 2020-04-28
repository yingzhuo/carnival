/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.restful.flow.signature;

import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.SignatureGenerationException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

/**
 * @author 应卓
 * @since 1.6.1
 */
public final class AlgorithmGenerators {

    public static AlgorithmGenerator none() {
        return () -> new Algorithm("none", "none") {
            @Override
            public void verify(DecodedJWT jwt) throws SignatureVerificationException {

            }

            @Override
            public byte[] sign(byte[] contentBytes) throws SignatureGenerationException {
                return new byte[0];
            }
        };
    }

    public static AlgorithmGenerator hmac256(String secret) {
        return () -> Algorithm.HMAC256(secret);
    }

    public static AlgorithmGenerator hmac384(String secret) {
        return () -> Algorithm.HMAC384(secret);
    }

    public static AlgorithmGenerator hmac512(String secret) {
        return () -> Algorithm.HMAC512(secret);
    }

    // -----------------------------------------------------------------------------------------------------------------

    private AlgorithmGenerators() {
    }

}
